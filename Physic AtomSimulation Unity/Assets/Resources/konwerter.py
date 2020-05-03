


def main():
    try:
        plik = open('atoms400.txt')
        lst = plik.read()
        #lst = lst.replace("]]", "]")
        #lst = lst.replace("[", "")
        #lst = lst.replace("\n", "")
        lst = lst.split("\n")
        
    finally:
        plik.close()

    kappa = []
    for i in lst:
        i = i.replace(".", ",")
        i = i.replace("E", "*10^")
        i = "=" + i
        kappa.append(i)

    for j in kappa:
        print(j)
    text_file = open("wyniki.txt", "w")
    text_file.write("\n".join(kappa))

    
if __name__ == "__main__":
    main()
