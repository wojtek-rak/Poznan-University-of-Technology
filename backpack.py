def uzupelnij(tst):
    l = 0
    for i in tst:
        i.append(i[1] / i[0])
        i.append(l)
        l += 1
    return tst
def greedy(x,b):
        x=sorted(x,key=lambda i:i[2])
        cena=0
        wypelnienie=0
        wybrane=[]
        while 1:
            tmp=x.pop()
            wypelnienie+=tmp[0]
            cena+=tmp[1]
            wybrane.append(tmp[3])
            if wypelnienie>b:
                wypelnienie -= tmp[0]
                cena -= tmp[1]
                wybrane.pop()
                break
        return (wypelnienie,cena,wybrane)
def dyn(x,b):
    #########################################generowanie macierzy
    tab=[[]]
    for i in range(b+1):
        tab[0].append(0)
    for i in range(1,len(x)+1):
        tab.append([0])
        for j in range(1,b+1):
            tab[i].append(0)
            if j<x[i-1][0]:
                tab[i][j]=tab[i-1][j]
            else:
                tab[i][j]=max(tab[i-1][j-x[i-1][0]]+x[i-1][1],tab[i-1][j])
    ########################################################################
    for i in tab:
        print(i)
    #szukanie odpowiedzi
    cena=tab[-1][-1]
    aktcena=cena
    wybrane=[]
    wypelnienie=0
    i=len(tab)-1
    tmp=i
    while aktcena>0:
        if aktcena not in tab[i]:
            wybrane.append(tmp-1)
            aktcena-=x[i][1]
            wypelnienie+=x[i][0]
        else:
            tmp=i
            i -= 1

    return (wypelnienie,cena,wybrane)


#testy Machowiaka
tst=[[3,5],[2,3],[4,4],[3,4],[1,2]]
tst1=[[5,6],[4,3],[3,2],[2,5],[5,4]]
###########################
#TUTAJ WKLEIĆ GENERATOR TAKICH TABLIC
###########################



#funkcja dopisuje do tablicy wage dla zachlannego i index
uzupelnij(tst)







print(tst)
print(greedy(tst,8))
print(tst)
print(dyn(tst,8))



"""
5 - n
3 2 4 3 1 - rozmiar
5 3 4 4 2 - wartosc
8 - b
"""

