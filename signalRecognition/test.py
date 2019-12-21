import os
from subprocess import Popen, PIPE

arr = os.listdir('trainall')
print(arr)
count = 0
good = 0
for p in arr:
    process = Popen(["python", ".\inf136789_inf136768.py", "trainall\\" + p], stdout=PIPE)
    (output, err) = process.communicate()
    out = str(output)
    out = out[2:3]
    t = p[4:5]
    print(str(count) + ' G: ' + t + ' ' + out)
    count += 1
    if t == out:
        good += 1

print(good)
print(count)
#f = os.system('python .\inf136789_inf136768.py trainall\001_K.wav')


##