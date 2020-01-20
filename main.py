import time
import Adafruit_CharLCD as LCD




import RPi.GPIO as GPIO
#from keypad import keypad
GPIO.setwarnings(False)
#kp = keypad(columnCount=3)



GPIO.setup(40, GPIO.IN, pull_up_down=GPIO.PUD_DOWN) #

# Raspberry Pi pin setup
lcd_rs = 2
lcd_en = 3
lcd_d4 = 4
lcd_d5 = 24
lcd_d6 = 23
lcd_d7 = 18
#lcd_backlight = 2

# Define LCD column and row size for 16x2 LCD.
lcd_columns = 16
lcd_rows = 2

lcd = LCD.Adafruit_CharLCD(lcd_rs, lcd_en, lcd_d4, lcd_d5, lcd_d6, lcd_d7, lcd_columns, lcd_rows)


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
    digit = 10# waitForMenu()
    print('wait for button')
    while True:
        if GPIO.input(40) == GPIO.HIGH:
            print("Button was pushed!")

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
    lcd.clear()
    lcd.message('Main menu!')
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


if __name__== "__main__":
    global lcd

    lcd.clear()
    lcd.message('Hello\nworld!')
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