import time
import Adafruit_CharLCD as LCD




import RPi.GPIO as GPIO
#from keypad import keypad
GPIO.setwarnings(False)
#kp = keypad(columnCount=3)



GPIO.setup(21, GPIO.IN, pull_up_down=GPIO.PUD_DOWN) #
GPIO.setup(20, GPIO.IN, pull_up_down=GPIO.PUD_DOWN) #
GPIO.setup(16, GPIO.IN, pull_up_down=GPIO.PUD_DOWN) #
GPIO.setup(12, GPIO.IN, pull_up_down=GPIO.PUD_DOWN) #

# Raspberry Pi pin setup
lcd_rs = 2
lcd_en = 3
lcd_d4 = 4
lcd_d5 = 24
lcd_d6 = 23
lcd_d7 = 18
#lcd_backlight = 2

button1 = 0
button2 = 0
button3 = 0
button4 = 0

buttonPress = 0

menuState = 0
highScoreState = 0
# Define LCD column and row size for 16x2 LCD.
lcd_columns = 16
lcd_rows = 2

lcd = LCD.Adafruit_CharLCD(lcd_rs, lcd_en, lcd_d4, lcd_d5, lcd_d6, lcd_d7, lcd_columns, lcd_rows)


# ZMIENNE GLOBALNE

gameDifficult = 1
highScore = 0

# ZMIENNE GLOBALNE

# KURWA DIODA

GPIO.setup(19, GPIO.OUT)

dioda = GPIO.PWM(19, 50)
wypelnienie = 0
dioda.start(wypelnienie)

# KURWA DIODA


def addToMenuState():
    global menuState
    if(menuState == 3):
        menuState = 0
    else:
        menuState +=1

def removeFromMenuState():
    global menuState
    if(menuState == 0):
        menuState = 3
    else:
        menuState -=1

def removeFromDifficulty():
    global gameDifficult
    if(gameDifficult == 1):
        gameDifficult = 1
    else:
        gameDifficult -= 1

def addToDifficulty():
    global gameDifficult
    if (gameDifficult == 6):
        gameDifficult = 6
    else:
        gameDifficult += 1

def zeroButtons():
    global button1
    global button2
    global button3
    global button4
    button1 = 0
    button2 = 0
    button3 = 0
    button4 = 0

def waitForMenu():
    global buttonPress
    global button1
    global button2
    global button3
    global button4
    while buttonPress == 0:
        if GPIO.input(21) == GPIO.HIGH:
            button1 = 1
            buttonPress = 1
        if GPIO.input(20) == GPIO.HIGH:
            button2 = 1
            buttonPress = 1
        if GPIO.input(16) == GPIO.HIGH:
            button3 = 1
            buttonPress = 1
        if GPIO.input(12) == GPIO.HIGH:
            button4 = 1
            buttonPress = 1
    buttonPress = 0


def mainMenu():
    try:
        while True:
            wypelnienie += 5
            if wypelnienie > 100:
                wypelnienie = 0
            print('Run forest run')
            dioda.ChangeDutyCycle(wypelnienie)
            time.sleep(0.05)
    except KeyboardInterrupt:
        print('Koniec')

    dioda.stop()
    GPIO.cleanup()

    return

    time.sleep(0.5)
    global menuState
    global buttonPress
    global button1
    global button2
    global button3
    global button4

    showMainMenu()
    waitForMenu()
    print('wait for button')


    if(button1 == 1):
        removeFromMenuState()

    elif(button2 == 1):
        addToMenuState()

    #elif (button3 == 1):
    #    runGameDifficulty()
    elif (button4 == 1):
        if (menuState == 0):
            startGame()
        elif (menuState == 1):
            runHighscore()
        elif (menuState == 2):
            runGameDifficulty()
        elif (menuState == 3):
            return

    zeroButtons()



    mainMenu()


def showMainMenu():
    lcd.clear()
    global menuState
    if (menuState == 0):
        lcd.message('Start game')
    elif (menuState == 1):
        lcd.message('Show highscore!')
    elif (menuState == 2):
        lcd.message('Change difficult')
    elif (menuState == 3):
        lcd.message('Quit')

    return

def runHighscore():
    time.sleep(0.5)
    global highScoreState
    showHighscore()
    waitForMenu()
    if (button1 == 1):
        if(highScoreState == 0):
            highScoreState == 1
        else:
            highScoreState = 0
    elif (button2 == 1):
        if(highScoreState == 0):
            highScoreState == 1
        else:
            highScoreState = 0
    elif (button4 == 1):
        if (highScoreState == 0):
            mainMenu()
        elif (highScoreState == 1):
            setHighScore(0)




    zeroButtons()
    runHighscore()

def showHighscore():
    global highScoreState
    lcd.clear()
    if(highScoreState == 0):
        lcd.message('HighScore: ' + str(getHighScore()) + '\n' + 'Go back')
    elif(highScoreState == 1):
        lcd.message('HighScore: ' + str(getHighScore()) + '\n' + 'Reset high score')
    # wyswietlanie na lcd
    return

def startGame():
    setHighScore(10)
    return

def resetHighScore():
    setHighScore(0)
    return

def runGameDifficulty():
    time.sleep(0.5)
    global gameDifficult
    showGameDifficulty()
    waitForMenu()

    if (button1 == 1):
        removeFromDifficulty()
    elif (button2 == 1):
        addToDifficulty()
    elif (button4 == 1):
        mainMenu()


    zeroButtons()
    runGameDifficulty()


def showGameDifficulty():
    global gameDifficult
    lcd.clear()
    lcd.message('Game difficult: '+ str(gameDifficult))
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