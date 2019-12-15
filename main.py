import time

import RPi.GPIO as GPIO
#from keypad import keypad
GPIO.setwarnings(False)
#kp = keypad(columnCount=3)

# LCD ZMIENNE

# Define GPIO to LCD mapping
LCD_RS = 2
LCD_E = 3
LCD_D4 = 4
LCD_D5 = 24
LCD_D6 = 23
LCD_D7 = 18

# Define some device constants
LCD_WIDTH = 16  # Maximum characters per line
LCD_CHR = True
LCD_CMD = False

LCD_LINE_1 = 0x80  # LCD RAM address for the 1st line
LCD_LINE_2 = 0xC0  # LCD RAM address for the 2nd line

# Timing constants
E_PULSE = 0.0005
E_DELAY = 0.0005

# LCD ZMIENNE



# ZMIENNE GLOBALNE

gameDifficult = 1
highScore = 0

# ZMIENNE GLOBALNE

def waitForMenu():
    digit = None
    while digit == None:
        #digit = kp.getKey()
        digit = int(input())

    return digit

def mainMenu():
    showMainMenu()
    digit = waitForMenu()

    if(digit == 1):
        startGame()
    elif(digit == 2):
        runHighscore()
    elif (digit == 7):
        runGameDifficulty()
    elif (digit == 8):
        return

    time.sleep(1)
    mainMenu()


def showMainMenu():
    #wyswietlanie na lcd
    print("Press 1 to start game, \nPress 2 to show highscore \nPress 7 to change difficult\nPress 8 to quit")
    return

def runHighscore():
    showHighscore()
    digit = waitForMenu()

    if (digit == 4):
        mainMenu()
    elif (digit == 5):
        resetHighScore()
    else:
        time.sleep(1)
        runHighscore()

def showHighscore():
    print("Highscore: " + str(getHighScore()) + "\nPress 4 to go back, \n Press 5 reset high score")
    # wyswietlanie na lcd
    return

def startGame():
    setHighScore(10)
    return

def resetHighScore():
    setHighScore(0)
    return

def runGameDifficulty():
    global gameDifficult
    showGameDifficulty()
    digit = waitForMenu()
    if (digit in [1, 2, 3, 4, 5, 6]):
        gameDifficult = digit
        return
    else:
        time.sleep(1)
        runGameDifficulty()


def showGameDifficulty():
    global gameDifficult
    print("Actual game difficult is: " + str(gameDifficult) + "\nPress 1-6 to set game difficult")

    # wyswietlanie na lcd
    return

def getHighScore():
    global highScore
    return highScore

def setHighScore(number):
    global highScore
    highScore = number
    print("HighScore set to " + str(number))


def lcd_init():
    # Initialise display
    lcd_byte(0x33, LCD_CMD)  # 110011 Initialise
    lcd_byte(0x32, LCD_CMD)  # 110010 Initialise
    lcd_byte(0x06, LCD_CMD)  # 000110 Cursor move direction
    lcd_byte(0x0C, LCD_CMD)  # 001100 Display On,Cursor Off, Blink Off
    lcd_byte(0x28, LCD_CMD)  # 101000 Data length, number of lines, font size
    lcd_byte(0x01, LCD_CMD)  # 000001 Clear display
    time.sleep(E_DELAY)


def lcd_byte(bits, mode):
    # Send byte to data pins
    # bits = data
    # mode = True  for character
    #        False for command

    GPIO.output(LCD_RS, mode)  # RS

    # High bits
    GPIO.output(LCD_D4, False)
    GPIO.output(LCD_D5, False)
    GPIO.output(LCD_D6, False)
    GPIO.output(LCD_D7, False)
    if bits & 0x10 == 0x10:
        GPIO.output(LCD_D4, True)
    if bits & 0x20 == 0x20:
        GPIO.output(LCD_D5, True)
    if bits & 0x40 == 0x40:
        GPIO.output(LCD_D6, True)
    if bits & 0x80 == 0x80:
        GPIO.output(LCD_D7, True)

    # Toggle 'Enable' pin
    lcd_toggle_enable()

    # Low bits
    GPIO.output(LCD_D4, False)
    GPIO.output(LCD_D5, False)
    GPIO.output(LCD_D6, False)
    GPIO.output(LCD_D7, False)
    if bits & 0x01 == 0x01:
        GPIO.output(LCD_D4, True)
    if bits & 0x02 == 0x02:
        GPIO.output(LCD_D5, True)
    if bits & 0x04 == 0x04:
        GPIO.output(LCD_D6, True)
    if bits & 0x08 == 0x08:
        GPIO.output(LCD_D7, True)

    # Toggle 'Enable' pin
    lcd_toggle_enable()


def lcd_toggle_enable():
    # Toggle enable
    time.sleep(E_DELAY)
    GPIO.output(LCD_E, True)
    time.sleep(E_PULSE)
    GPIO.output(LCD_E, False)
    time.sleep(E_DELAY)


def lcd_string(message, line):
    # Send string to display

    message = message.ljust(LCD_WIDTH, " ")

    lcd_byte(line, LCD_CMD)

    for i in range(LCD_WIDTH):
        lcd_byte(ord(message[i]), LCD_CHR)


def initLcd():
    # Main program block
    GPIO.setwarnings(False)
    GPIO.setmode(GPIO.BCM)  # Use BCM GPIO numbers
    GPIO.setup(LCD_E, GPIO.OUT)  # E
    GPIO.setup(LCD_RS, GPIO.OUT)  # RS
    GPIO.setup(LCD_D4, GPIO.OUT)  # DB4
    GPIO.setup(LCD_D5, GPIO.OUT)  # DB5
    GPIO.setup(LCD_D6, GPIO.OUT)  # DB6
    GPIO.setup(LCD_D7, GPIO.OUT)  # DB7

    # Initialise display
    lcd_init()


if __name__== "__main__":
    initLcd()
    lcd_string("Rasbperry Pi", LCD_LINE_1)
    lcd_string("16x2 LCD Test", LCD_LINE_2)

    time.sleep(3)
    mainMenu()


    #
    # ###### 4 Digit wait ######
    # seq = []
    # for i in range(4):
    #     digit = None
    #     while digit == None:
    #         digit = kp.getKey()
    #     seq.append(digit)
    #     time.sleep(0.4)
    #
    # # Check digit code
    # print(seq)
    # if seq == [1, 2, 3, '#']:
    #     print( "Code accepted")