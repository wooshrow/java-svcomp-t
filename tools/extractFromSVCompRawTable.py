#
# Utility to extract verdict-results from sv-comp tables.
# Manually-copy paster sv-comp table from web to a text file.
# Then use this to extract verdicts of participating tools and
# save them into a csv file.
#

from pathlib import Path
import sys
import csv


def extractKill(file) :
    with open(file) as f:
       data = f.readlines()
    k = 0
    N = len(data)
    cnt = 0
    results = []
    while k<N :
        problem = data[k].strip()
        if problem.strip() == "" : break
        j = k+1
        COASTAL = data[j].strip()
        j += 4
        GDART = data[j].strip()
        j += 4
        JRanger = data[j].strip()
        j += 4
        JayHorn = data[j].strip()
        j += 4
        JBMC = data[j].strip()
        j += 4
        JDart = data[j].strip()
        j += 4
        MLB = data[j].strip()
        j += 4
        SWS = data[j].strip()
        j += 4
        SWAT = data[j].strip()
        X = { 'P' : problem , 'COASTAL' : COASTAL, 'GDART' : GDART,
              'JRanger' : JRanger, 'JayHorn' : JayHorn, 'JBMC' : JBMC,
              'JDart' : JDart, 'MLB' : MLB, 'SWS' : SWS, 'SWAT' : SWAT }
        print(f"== {cnt+1} {problem}")
        #print(f"== {cnt+1} {X}")
        results.append(X)
        k += 37
        cnt += 1

    # save to csvfile
    #print(results)
    fname = "extracted.csv"
    with open(fname, 'w', newline='') as csvfile:
       fieldnames = ['P', 'COASTAL', 'GDART', 'JRanger', 'JayHorn',
                     'JBMC', 'JDart', 'MLB', 'SWS', 'SWAT' ]
       writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
       writer.writeheader()
       writer.writerows(results)

if __name__ == '__main__':
   extractKill(sys.argv[1])
