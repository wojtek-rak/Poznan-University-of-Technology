import time


import RPi.GPIO as GPIO
from keypad import keypad
GPIO.setwarnings(False)
kp = keypad(columnCount=3)

gameDifficult = 1
highScore = 0

def waitForMenu():
    digit = None
    while digit == None:
        digit = kp.getKey()
        #digit = int(input())

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

    # Print result
    print(digit)
    time.sleep(1)
    mainMenu()

def showMainMenu():
    #wyswietlanie na lcd
    print("Press 1 to start game, \nPress 2 to show highscore \nPress 7 to change difficult")
    return

def runHighscore():
    showHighscore()
    digit = waitForMenu()

    if (digit == 4):
        mainMenu()
    elif (digit == 5):
        resetHighScore()
    else:
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