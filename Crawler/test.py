import urllib2
import re
from bs4 import BeautifulSoup
from wheel.util import utf8
from crawler import response
 
response = urllib2.urlopen("http://www.flyertalk.com/forum/credit-card-programs-599")

html1=response.read()

soup=BeautifulSoup(html1,"lxml")

##use html <a> as tags
'''
for links in soup.find_all('a'):
    print (links.get('href'))
    '''
pattern = re.compile(r'http://www.flyertalk.com/forum/credit-card-programs/1')
'''
for links in soup.find_all('a'):
    link=links.get('href')
    match1 = pattern.match(str(link))
    if match1:
         print (match1.string)
        '''
       


##next step is to keep the first comment , throw all the other comments

#set a string for compare at links
AddrList = ['']

for links in soup.find_all('a'):
    link=links.get('href')
    #match = re.findall(pattern, link)
   # print match
   # print(link)
   # print('------------------')
    match1 = pattern.match(str(link))

    if match1:
         #print (match1.string)
         singleAddress=match1.string[:-5]
         AddrList.append(singleAddress)
         #print singleAddress
      
tempString = 'orignial string'  
AddrList.remove("")
for element1 in AddrList:
    #tempString = element1
    pattern = re.compile(tempString)
    match1 = pattern.match(str(element1))
    
    if match1:
        AddrList.remove(element1)  
    else:        
        tempString = element1
#why i need that twice? i don't know, because i can not delete the even entries correctly
for element1 in AddrList:
    #tempString = element1
    pattern = re.compile(tempString)
    match1 = pattern.match(str(element1))
    
    if match1:
        AddrList.remove(element1)
        
    else:        
        tempString = element1
   
#why i need that twice? i don't know, because i can not delete the even entries correctly
for element1 in AddrList:
    #tempString = element1
    pattern = re.compile(tempString)
    match1 = pattern.match(str(element1))
    
    if match1:
        AddrList.remove(element1)        
    else:        
        tempString = element1
#why i need that twice? i don't know, because i can not delete the even entries correctly
for element1 in AddrList:
    #tempString = element1
    pattern = re.compile(tempString)
    match1 = pattern.match(str(element1))
    
    if match1:
        AddrList.remove(element1)
      
    else:        
        tempString = element1

#why i need that twice? i don't know, because i can not delete the even entries correctly
for element1 in AddrList:
    #tempString = element1
    pattern = re.compile(tempString)
    match1 = pattern.match(str(element1))
    
    if match1:
        AddrList.remove(element1)
        
    else:        
        tempString = element1

#why i need that twice? i don't know, because i can not delete the even entries correctly
for element1 in AddrList:
    #tempString = element1
    pattern = re.compile(tempString)
    match1 = pattern.match(str(element1))
    
    if match1:
        AddrList.remove(element1)
    else:        
        tempString = element1
        
 ## All the addresses are stored in the AddrList 
myfile = open('docset.trecweb', 'w')   # The file is newly created where foo.py is    

for element1 in AddrList:
    
    #print element1
    element1+='.html'
    print element1
    response = urllib2.urlopen(element1)
    
    


    #html2=response.read()

    #soupOfContent=BeautifulSoup(html2,"lxml")
    
    
    
    rawContent = "<DOC>\n"
    rawContent += "<DOCNO>" + element1 + "</DOCNO>\n"
    
    
    #use beautiful soup to get content of html page
    soup=BeautifulSoup(response,"lxml")
    messageOfPost = soup.find_all("div")
    
#     for links in soup.find_all('a'):
#     link=links.get('href')
    for messageSingle in messageOfPost:
        print messageSingle
        
        
    rawContent += soup.get_text();
    #rawContent += response.read()
    
    rawContent += "\n </DOC>\n\n"
    ##print html, write raw content to the file
    # ... write to myfile ...
    #remove some un-standard char
    myfile.write(rawContent.encode('utf-8'))
myfile.close()      
    


        


