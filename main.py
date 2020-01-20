import time
import Adafruit_CharLCD as LCD




import RPi.GPIO as GPIO
#from keypad import keypad
GPIO.setwarnings(False)
#kp = keypad(columnCount=3)
import random



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

GPIO.setup(26, GPIO.OUT)
GPIO.setup(19, GPIO.OUT)
GPIO.setup(13, GPIO.OUT)
GPIO.setup(6, GPIO.OUT)
GPIO.setup(5, GPIO.OUT)
GPIO.setup(22, GPIO.OUT)

dioda1 = GPIO.PWM(26, 50)
dioda2 = GPIO.PWM(19, 50)
dioda3 = GPIO.PWM(13, 50)
dioda4 = GPIO.PWM(6, 50)
dioda5 = GPIO.PWM(5, 50)
dioda6 = GPIO.PWM(22, 50)

dioda1.start(0)
dioda2.start(0)
dioda3.start(0)
dioda4.start(0)
dioda5.start(0)
dioda6.start(0)



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

    dioda1.ChangeDutyCycle(100)
    dioda2.ChangeDutyCycle(100)
    dioda3.ChangeDutyCycle(100)
    dioda4.ChangeDutyCycle(100)
    dioda5.ChangeDutyCycle(100)
    dioda6.ChangeDutyCycle(100)


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
            dioda1.stop()
            dioda2.stop()
            dioda3.stop()
            dioda4.stop()
            dioda5.stop()
            dioda6.stop()

            GPIO.cleanup()
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

def road(num):
    lev = []
    for i in range(num):
        rand = random.uniform(0, 1)
        if(rand < 0.25):
            lev.append(1)

        elif(rand >= 0.25 and rand < 0.5):
            lev.append(2)

        elif(rand >= 0.5 and rand < 0.75):
            lev.append(3)

        elif (rand >= 0.75):
            lev.append(4)
    return lev

def startGame():
    level = 4
    global gameDifficult





    setHighScore(10)
    return

























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