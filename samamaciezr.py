import random
import numpy as np

def calc(mc, quan):
    sum = 0
    for i in range(quan):
        for j in range(quan):
            if mc[i][j] != 0:
                sum += 1
    return sum

def main():
    randomowe = []

    lst = []

    sizeof = 10

    size = sizeof
    it = 1
    break_point = False
    for i in range(5):
        x = np.zeros((size, size)).astype(int)
        while (it < size):
            for j in range(size - it):
                if calc(x, size) / (size * size) > 0.7:
                    break_point = True
                    break
                ran = random.randint(1, 1000)
                x[j][j + it] = ran
                x[j + it][j] = ran
            if (break_point):
                break
            it += 1

        size += sizeof
        it = 1
        break_point = False

        lst.append(x)

    print(lst)



if __name__ == "__main__":
    main()
