import urllib2
import re
from bs4 import BeautifulSoup
from wheel.util import utf8
from crawler import response


import sys
reload(sys)
sys.setdefaultencoding('utf-8')
 
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
    element1+='.html'
    print element1
    response = urllib2.urlopen(element1)

    rawContent = "<DOC>\n"
    rawContent += "<DOCNO>" + element1 + "</DOCNO>\n"
    
    
    #use beautiful soup to get content of html page
#     soup=BeautifulSoup(response,"lxml")
#     messageOfPost = soup.find_all("div")
#     
# #     for links in soup.find_all('a'):
# #     link=links.get('href')
#     for messageSingle in messageOfPost:
#         print messageSingle
#         
#         

    soup = BeautifulSoup(response, 'html.parser')
    texts = soup.findAll(text=True)
    def visible(element):
        if element.parent.name in ['style', 'script', '[document]', 'head', 'title']:
            return False
        elif re.match('<!--.*-->', str(element)):
            return False
        return True

    visible_texts = filter(visible, texts)

    print visible_texts
    for SingleContent in visible_texts:
        rawContent += SingleContent
        #print rawContent
# 
#     rawContent += visible_texts
    #rawContent += soup.get_text();
    #rawContent += response.read()
    
    rawContent += "\n </DOC>\n\n"
    ##print html, write raw content to the file
    # ... write to myfile ...
    #remove some un-standard char
    myfile.write(rawContent.encode('utf-8'))
myfile.close()      
    


        


