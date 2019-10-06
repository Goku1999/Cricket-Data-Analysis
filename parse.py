# -*- coding: utf-8 -*-
"""

@author: Chitranshi and Sankalp
"""
import matplotlib.pyplot as plt
import glob
import numpy as np
import sys

#----------generate() starts-------------

def generate(start,end,matches,team1):
    c=input("press 1 for Runs Scored per Match\nPress 2 for wickets taken per match\nPress 3 for win/lose graph\nPress 4 for per team result\n")
    if int(c)==1:
        total=[]
        down=range(1,len(matches)+1)
        s=str(team)+" Score"
        for x in matches:
            for y in range(int(start),int(end)+1):
                if str(y) in x:
                     file=open(x)
                     for line in file:
                         l=line.split(",")
                         if l[0].lower().startswith(s.lower()):
                             total.append(int(l[1].split("/")[0]))
        down=down[0:len(total)]
        plt.bar(x=down,height=total)
        plt.xlabel("Match Number", fontsize=18)
        plt.ylabel("Runs Scored",fontsize=18)
        plt.title(str(team+" Score per match"))
        plt.figure(figsize=(20,50))
        #plt.show()
    elif int(c)==2:
        total=[]
        down=range(1,len(matches)+1)
        s=str(team)+" Score"
        for x in matches:
            for y in range(int(start),int(end)+1):
                if str(y) in x:
                     file=open(x)
                     for line in file:
                         l=line.split(",")
                         if l[0].lower().startswith(s.lower()):
                             total.append(int(l[1].split("/")[1]))
        down=down[0:len(total)]
        plt.bar(x=down,height=total)
        plt.xlabel("Match Number", fontsize=18)
        plt.ylabel("Wickets taken",fontsize=18)
        plt.title(str(team+" Wixkets per match"))
        plt.figure(figsize=(20,50))
        #plt.show()
    elif int(c)==3:        
        win=0
        lost=0
        draw=0
        for x in matches:
            for y in range(int(start),int(end)+1):
                if str(y) in x:
                     file=open(x)
                     for line in file:
                         l=line.split(",")
                         if l[0].lower()=="winner":
                             if l[1].lower().startswith(team.lower()):
                                 win=win+1
                             elif l[1].lower().startswith("draw") :
                                 draw=draw+1
                             else:
                                 lost=lost+1
        plt.pie([win,lost,draw],explode=(0.1,0,0),labels=["Win","Lost","Draw"],colors=['green','red','blue'],shadow=True,startangle=140)
        plt.title(str(team+" Match Results"))
        plt.show()
    elif int(c)==4:
        teams=[]
        win=[0]*20
        lost=[0]*20
        draw=[0]*20
        i=0
        for x in matches:
            for y in range(int(start),int(end)+1):
                if str(y) in x:
                     file=open(x)
                     for line in file:
                         l=line.split(",")
                         if l[0].lower().startswith("team"):
                             if l[1].lower().startswith(team)==False:
                                 team2=l[1]
                                 if team2 in teams:
                                     i=teams.index(team2)
                                 else:
                                     teams.append(team2)
                                     i=teams.index(team2)
                         if l[0].lower()=="winner":
                             if l[1].lower().startswith(team.lower()):
                                 win[i]=win[i]+1
                             elif l[1].lower().startswith("draw") :
                                 draw[i]=draw[i]+1
                             else:
                                 lost[i]=lost[i]+1
        win=win[0:len(teams)]                   
        lost=lost[0:len(teams)]
        draw=draw[0:len(teams)]
        p1=plt.bar(x=teams,height=win, color='g')
        p2=plt.bar(x=teams, height=lost,bottom=np.array(win), color='r')
        p3=plt.bar(x=teams,height=draw, bottom=np.array(win)+np.array(lost), color='b')
        plt.xlabel("Teams", fontsize=18)
        plt.ylabel("Results",fontsize=18)
        plt.title(str(team+" per Team Match Results"))
        plt.legend((p1[0],p2[0],p3[0]),("Win","Lost","Draw"))
        plt.figure(figsize=(20,50))
        plt.show()

#-------------------------generate() ends----------------------------------------

#---------------------------------overloaded generate() starts---------------

def generate1(start,end,matches,team1,team2):
    c=input("press 1 for Runs Scored per Match\nPress 2 for wickets taken per match\nPress 3 for win/lose ratio\n")
    if int(c)==1:
        total1=[]
        total2=[]
        count=0
        s1=str(team1)+" Score"
        s2=str(team2)+" Score"
        for x in matches:
            for y in range(int(start),int(end)+1):
                if str(y) in x:
                     file=open(x)
                     for line in file:
                         l=line.split(",")
                         if l[0].lower().startswith(s1.lower()):
                             count=count+1
                             total1.append(int(l[1].split("/")[0]))
                         elif l[0].lower().startswith(s2.lower()):
                             total2.append(int(l[1].split("/")[0]))
        down=np.arange(count)
        fig,ax=plt.subplots()
        p1=plt.bar(down,total1,0.4,color='g',label=team1)
        plt.xlabel("Match Number", fontsize=18)
        plt.ylabel("Runs Scored",fontsize=18)
        p2=plt.bar(down+0.4,total2,0.4,color='r',label=team2)
        plt.title(str(team2+" Score per match"))
        plt.legend()
        plt.tight_layout()
        plt.show()
        #plt.show()
    elif int(c)==2:
        total1=[]
        total2=[]
        count=0
        s1=str(team1)+" Score"
        s2=str(team2)+" Score"
        for x in matches:
            for y in range(int(start),int(end)+1):
                if str(y) in x:
                     file=open(x)
                     for line in file:
                         l=line.split(",")
                         if l[0].lower().startswith(s1.lower()):
                             count=count+1
                             total1.append(int(l[1].split("/")[1]))
                         elif l[0].lower().startswith(s2.lower()):
                             total2.append(int(l[1].split("/")[1]))
        down=np.arange(count)
        fig,ax=plt.subplots()
        p1=plt.bar(down,total1,0.4,color='g',label=team1)
        plt.xlabel("Match Number", fontsize=18)
        plt.ylabel("Wickets Taken",fontsize=18)
        p2=plt.bar(down+0.4,total2,0.4,color='r',label=team2)
        plt.title(str(team2+" Wixkets per match"))
        plt.legend()
        plt.tight_layout()
        plt.show()
    elif int(c)==3:        
        win=0
        lost=0
        draw=0
        for x in matches:
            for y in range(int(start),int(end)+1):
                if str(y) in x:
                     file=open(x)
                     for line in file:
                         l=line.split(",")
                         if l[0].lower()=="winner":
                             if l[1].lower().startswith(team1.lower()):
                                 win=win+1
                             elif l[1].lower().startswith("draw") :
                                 draw=draw+1
                             else:
                                 lost=lost+1
        plt.pie([win,draw,lost],labels=[team1,"Draw",team2],colors=['green','red','blue'],shadow=True,startangle=140)
        plt.show()
    elif int(c)==4:
        teams=[]
        win=[0]*20
        lost=[0]*20
        draw=[0]*20
        i=0
        for x in matches:
            for y in range(int(start),int(end)+1):
                if str(y) in x:
                     file=open(x)
                     for line in file:
                         l=line.split(",")
                         if l[0].lower().startswith("team"):
                             if l[1].lower().startswith(team)==False:
                                 team2=l[1]
                                 if team2 in teams:
                                     i=teams.index(team2)
                                 else:
                                     teams.append(team2)
                                     i=teams.index(team2)
                         if l[0].lower()=="winner":
                             if l[1].lower().startswith(team.lower()):
                                 win[i]=win[i]+1
                             elif l[1].lower().startswith("draw") :
                                 draw[i]=draw[i]+1
                             else:
                                 lost[i]=lost[i]+1
        win=win[0:len(teams)]                   
        lost=lost[0:len(teams)]
        draw=draw[0:len(teams)]
        p1=plt.bar(x=teams,height=win, color='g')
        p2=plt.bar(x=teams, height=lost,bottom=np.array(win), color='r')
        p3=plt.bar(x=teams,height=draw, bottom=np.array(win)+np.array(lost), color='b')
        plt.xlabel("Teams", fontsize=18)
        plt.ylabel("Results",fontsize=18)
        plt.legend((p1[0],p2[0],p3[0]),("Win","Lost","Draw"))
        plt.figure(figsize=(20,50))
        plt.show()
#----------------------overloaded generate() ends-------------------------------

#----------------------------player info----------------------------------------
def playerInfo(start,end,matches,team1,initial,last):
    played=0
    count=False
    total=[]
    high=0
    for x in matches:
            for y in range(int(start),int(end)+1):
                if str(y) in x:
                     file=open(x)
                     count=False
                     for line in file:
                         l=line.split(",")
                         if l[0].lower().startswith(initial.lower()) and l[0].lower().endswith(last.lower()):
                             if count==False:
                                 played=played+1
                                 count=True
                             try:
                                total.append(int(l[1]))
                                if int(l[1])>high:
                                    high=int(l[1])
                             except:
                                continue
    down=np.arange(len(total))
    plt.bar(x=down,height=total)
    plt.xlabel("Number of matches",fontsize=18)
    plt.ylabel("Runs scored",fontsize=18)
    plt.title(str("Highest Score : "+str(high)))
#-----------------------player info ends----------------------------

dir="C:\\Users\\Sankalp\\Documents\\cricketDataCSV\\Clean\\*.csv"
f=(glob.glob(dir))
print("Match data availabe between 2006-2019")
c=input("Press 1 for team info\nPress 2 for comparing 2 teams\nPress 3 for player info\n")

#-----------------Single team info--------------------------------

if int(c)==1:
    team=input("Enter the name of team you want to study about from the teams given below\nAfghanistan\nAustralia\nBangladesh\nBermuda\nCanada\nEngland\nHong Kong\nIndia\nIreland\nKenya\nNetherlands\nNew Zeland\nPakistan\nScotland\nSouth Africa\nSri Lanka\nUnited Arab Emirates\nWest Indies\nZimbabwe\n")
    tn=team.replace(" ","")
    matches=[]
    for x in f:
        if tn.lower() in x.lower():
            matches.append(x)
    print("Total number of matches played by",team,"=",len(matches))
    choice=input("Press 1 for overall info\nPress 2 for a particular year info\nPress 3 for a range of year input\n")
    if int(choice)==1:
        generate(2006,2019,matches,team)
    elif int(choice)==2:
        while True:
            year=input("Enter year (2006-2019)\n")
            if int(year)<2006 or int(year)>2019:
                print("Enter correct year")
                continue
            else:
                break
        generate(year,year,matches,team)
    elif int(choice)==3:
        start=input("Enter start year (2006 onwards)\n")
        end=input("Enter end year (Till 2019)\n")
        generate(int(start),int(end),matches,team)
        
#-----------------------------Two Team comparison-------------------------------

elif int(c)==2:
    print("Afghanistan\nAustralia\nBangladesh\nBermuda\nCanada\nEngland\nHong Kong\nIndia\nIreland\nKenya\nNetherlands\nNew Zeland\nPakistan\nScotland\nSouth Africa\nSri Lanka\nUnited Arab Emirates\nWest Indies\nZimbabwe\n")
    team1=input("Enter name of Team1\n")
    team2=input("Enter name of Team2\n")
    tn1=team1.replace(" ","")
    tn2=team2.replace(" ","")
    matches=[]
    for x in f:
        if tn1.lower() in x.lower() and tn2.lower() in x.lower():
            matches.append(x)
    print("Total number of matches played between",team1,"and",team2,"=",len(matches))
    if len(matches)==0:
        print("No matches are played till date between",team1,"and",team2)
        sys.exit()
    choice=input("Press 1 for overall info\nPress 2 for a particular year info\nPress 3 for a range of year input\n")
    if int(choice)==1:
        generate1(2006,2019,matches,team1,team2)
    elif int(choice)==2:
        while True:
            year=input("Enter year (2006-2019)\n")
            if int(year)<2006 or int(year)>2019:
                print("Enter correct year")
                continue
            else:
                break
        generate1(year,year,matches,team1,team2)
    elif int(choice)==3:
        start=input("Enter start year (2006 onwards)\n")
        end=input("Enter end year (Till 2019)\n")
        generate1(int(start),int(end),matches,team1,team2)
#-----------------------------Player info--------------------------------------
elif int(c)==3:
    print("Afghanistan\nAustralia\nBangladesh\nBermuda\nCanada\nEngland\nHong Kong\nIndia\nIreland\nKenya\nNetherlands\nNew Zeland\nPakistan\nScotland\nSouth Africa\nSri Lanka\nUnited Arab Emirates\nWest Indies\nZimbabwe\n")
    team=input("Enter name of team with whom the player is associated\n")
    print("Enter first initial and last name of player (Example: S Tendulkar for Sachin Tendulkar)")
    initial=input("Enter first initial\n")
    player=input("Enter last name\n")
    tn=team.replace(" ","")
    matches=[]
    for x in f:
        if tn.lower() in x.lower():
            matches.append(x)
    choice=input("Press 1 for overall info\nPress 2 for a particular year info\nPress 3 for a range of year input\n")
    if int(choice)==1:
        playerInfo(2006,2019,matches,team,initial,player)
    elif int(choice)==2:
        while True:
            year=input("Enter year (2006-2019)\n")
            if int(year)<2006 or int(year)>2019:
                print("Enter correct year")
                continue
            else:
                break
        playerInfo(year,year,matches,team,initial,player)
    elif int(choice)==3:
        start=input("Enter start year (2006 onwards)\n")
        end=input("Enter end year (Till 2019)\n")
        playerInfo(int(start),int(end),matches,team,initial,player)