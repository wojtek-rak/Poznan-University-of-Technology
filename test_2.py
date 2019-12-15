import Adafruit_CharLCD as LCD
import time

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
lcd.clear()
time.sleep(5.0)
lcd.message('Hello\nworld!')
# Wait 5 seconds

time.sleep(5.0)
lcd.clear()
text = "Maciej Meler" #raw_input("Type Something to be displayed: ")
lcd.message(text)

# Wait 5 seconds
time.sleep(5.0)
lcd.clear()
lcd.message('Goodbye\nWorld!')

time.sleep(5.0)
lcd.clear()