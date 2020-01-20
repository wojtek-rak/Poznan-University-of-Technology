import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BOARD)


GPIO.setwarnings(False)
GPIO.setup(19, GPIO.OUT)

dioda = GPIO.PWM(19, 50)
wypelnienie = 0
dioda.start(wypelnienie)

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