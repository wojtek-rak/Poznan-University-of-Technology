import time
#import RPi.GPIO as GPIO
#from keypad import keypad

#GPIO.setwarnings(False)

#kp = keypad(columnCount=3)

gameDifficult = 1
highScore = 0

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
    elif (digit == 3):
        runGameDifficulty()

    # Print result
    print(digit)
    time.sleep(0.5)

def showMainMenu():
    #wyswietlanie na lcd
    return

def runHighscore():
    showHighscore()
    digit = waitForMenu()

    if (digit == 1):
        mainMenu()
    elif (digit == 2):
        resetHighScore()

def showHighscore():
    # wyswietlanie na lcd
    print(getHighScore())
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
        runGameDifficulty()


def showGameDifficulty():
    global gameDifficult
    print(gameDifficult)
    # wyswietlanie na lcd
    return

def getHighScore():
    global highScore
    return highScore

def setHighScore(number):
    global highScore
    highScore = number

if __name__== "__main__":
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