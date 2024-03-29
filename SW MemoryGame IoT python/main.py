import time
import Adafruit_CharLCD as LCD
import sqlite3
import RPi.GPIO as GPIO
import random
#from keypad import keypad
GPIO.setwarnings(False)
#kp = keypad(columnCount=3)


buzzer = 27
GPIO.setup(buzzer, GPIO.OUT)

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

gameDifficult = 4
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
    if (gameDifficult == 8):
        gameDifficult = 8
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

def buzz():
    global buzzer
    GPIO.output(buzzer, GPIO.HIGH)
    time.sleep(0.15)  # Delay in seconds
    GPIO.output(buzzer, GPIO.LOW)
    time.sleep(0.15)

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
    buzz()

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
            dioda1.ChangeDutyCycle(0)
            dioda2.ChangeDutyCycle(0)
            dioda3.ChangeDutyCycle(0)
            dioda4.ChangeDutyCycle(0)
            dioda5.ChangeDutyCycle(0)
            dioda6.ChangeDutyCycle(0)
            zeroButtons()
            startGame()
        elif (menuState == 1):
            zeroButtons()
            runHighscore()
        elif (menuState == 2):
            zeroButtons()
            runGameDifficulty()
        elif (menuState == 3):
            dioda1.stop()
            dioda2.stop()
            dioda3.stop()
            dioda4.stop()
            dioda5.stop()
            dioda6.stop()
            lcd.clear()
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
    global button1
    global button2
    global button3
    global button4
    time.sleep(0.5)
    global highScoreState
    showHighscore()
    waitForMenu()
    if (button1 == 1):
        if(highScoreState == 0):
            highScoreState = 1
        else:
            highScoreState = 0
    elif (button2 == 1):
        if(highScoreState == 0):
            highScoreState = 1
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
    lcd.message('Game difficult:\n'+ str(gameDifficult))
    # wyswietlanie na lcd
    return

def getHighScore():
    db = sqlite3.connect('highScore.db')
    cur = db.cursor()
    cur.execute("SELECT * FROM highScore")

    rows = cur.fetchall()
    highScore = -1
    for row in rows:
        highScore = row[0]
        print(row[0])

    db.close()
    return highScore

def setHighScore(number):
    db = sqlite3.connect('highScore.db')
    cur = db.cursor()
    cur.execute("UPDATE highScore SET data =" + str(number))
    db.commit()
    db.close()
    print("HighScore set to " + str(number))

def getRoad(num):
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

def getTime():
    global gameDifficult

    return 2.0 / float(gameDifficult)

def preStart():
    for i in range(3):
        GPIO.output(buzzer, GPIO.HIGH)
        dioda5.ChangeDutyCycle(100)
        dioda6.ChangeDutyCycle(100)
        time.sleep(0.15)  # Delay in seconds
        dioda5.ChangeDutyCycle(0)
        dioda6.ChangeDutyCycle(0)
        GPIO.output(buzzer, GPIO.LOW)
        time.sleep(0.15)

def showRoad(road):
    for i in road:
        if(i == 1):
            dioda1.ChangeDutyCycle(100)
            time.sleep(getTime())
            dioda1.ChangeDutyCycle(0)
            time.sleep(getTime())

        elif(i == 2):
            dioda2.ChangeDutyCycle(100)
            time.sleep(getTime())
            dioda2.ChangeDutyCycle(0)
            time.sleep(getTime())

        elif (i == 3):
            dioda3.ChangeDutyCycle(100)
            time.sleep(getTime())
            dioda3.ChangeDutyCycle(0)
            time.sleep(getTime())

        elif (i == 4):
            dioda4.ChangeDutyCycle(100)
            time.sleep(getTime())
            dioda4.ChangeDutyCycle(0)
            time.sleep(getTime())

def enterRoad(count):
    global buttonPress
    global button1
    global button2
    global button3
    global button4
    buttonPress = 0
    enteredRoad = []
    while count != 0:
        if (buttonPress == 0):
            if GPIO.input(21) == GPIO.HIGH:
                dioda1.ChangeDutyCycle(100)
                button1 = 1
                buttonPress = 1
            if GPIO.input(20) == GPIO.HIGH:
                dioda2.ChangeDutyCycle(100)
                button2 = 1
                buttonPress = 1
            if GPIO.input(16) == GPIO.HIGH:
                dioda3.ChangeDutyCycle(100)
                button3 = 1
                buttonPress = 1
            if GPIO.input(12) == GPIO.HIGH:
                dioda4.ChangeDutyCycle(100)
                button4 = 1
                buttonPress = 1

        if(buttonPress == 1):
            if(button1 == 1):
                if(GPIO.input(21) == GPIO.LOW):
                    dioda1.ChangeDutyCycle(0)
                    button1 = 0
                    buttonPress = 0
                    count -= 1
                    enteredRoad.append(1)
                    print('append1')
                    time.sleep(0.25)

            if(button2 == 1):
                if (GPIO.input(20) == GPIO.LOW):
                    dioda2.ChangeDutyCycle(0)
                    button2 = 0
                    buttonPress = 0
                    count -= 1
                    enteredRoad.append(2)
                    print('append2')
                    time.sleep(0.25)

            if (button3 == 1):
                if (GPIO.input(16) == GPIO.LOW):
                    dioda3.ChangeDutyCycle(0)
                    button3 = 0
                    buttonPress = 0
                    count -= 1
                    enteredRoad.append(3)
                    print('append3')
                    time.sleep(0.25)

            if (button4 == 1):
                if (GPIO.input(12) == GPIO.LOW):
                    dioda4.ChangeDutyCycle(0)
                    button4 = 0
                    buttonPress = 0
                    count -= 1
                    enteredRoad.append(4)
                    print('append4')
                    time.sleep(0.25)

    return enteredRoad

def startGame():
    level = 4
    global gameDifficult
    global highScore
    end = 0

    while(end == 0):
        road = getRoad(level)
        preStart()
        showRoad(road)

        enteredRoad = enterRoad(len(road))

        identical = True

        for i in range(level):
            if(enteredRoad[i] != road[i]):
                identical = False

        if(identical):
            dioda5.ChangeDutyCycle(100)
            GPIO.output(buzzer, GPIO.HIGH)
            time.sleep(0.15)  # Delay in seconds
            GPIO.output(buzzer, GPIO.LOW)
            time.sleep(0.15)

            GPIO.output(buzzer, GPIO.HIGH)
            time.sleep(0.15)  # Delay in seconds
            GPIO.output(buzzer, GPIO.LOW)
            time.sleep(0.15)


            time.sleep(1.4)
            dioda5.ChangeDutyCycle(0)
            level += 1

            loop = False
            lcd.clear()
            lcd.message('Press to\nnext game')
            while (loop == False):
                if GPIO.input(12) == GPIO.HIGH:
                    loop = True
                if GPIO.input(20) == GPIO.HIGH:
                    loop = True
                if GPIO.input(16) == GPIO.HIGH:
                    loop = True
                if GPIO.input(21) == GPIO.HIGH:
                    loop = True



        else:
            end = 1
            lcd.clear()
            if(level > 4):
                lcd.message('You lost\nScore:' + str(level - 1))
                if(getHighScore() < level - 1):
                    setHighScore(level - 1)
            else:
                lcd.message('You lost\nScore:' + str(0))

            dioda6.ChangeDutyCycle(100)
            GPIO.output(buzzer, GPIO.HIGH)
            time.sleep(1)  # Delay in seconds
            GPIO.output(buzzer, GPIO.LOW)
            time.sleep(3)
            dioda6.ChangeDutyCycle(0)
            time.sleep(0.5)

    return


#dioda5.ChangeDutyCycle(100)
#dioda6.ChangeDutyCycle(100)



















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