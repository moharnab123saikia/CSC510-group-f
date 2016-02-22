import urllib2
#import xml.etree.ElementTree as etree
from bs4 import BeautifulSoup
import requests

url = 'http://www.yelp.com/biz/guasaca-raleigh'

google_obj = {}
jsonArray = []
page = requests.get(url)
soup = BeautifulSoup(page.text,'html.parser')
for e in soup.findAll('br'):
    e.extract()	
#print soup

header = soup.find("ul", class_="ylist-bordered")
info = header.find_all("li", recursive=False)

cnt = 0;
for review in info:
	wrap = review.find("div", class_="review-wrapper").find("p", {"itemprop":"description"})
	if wrap is not None: 
		text = wrap.contents[0]
		scale = review.find("div", class_="review-wrapper").find("meta", {"itemprop":"ratingValue"})['content']
		jsonArray.append({"scale":scale, "review":text})
		cnt = cnt + 1
		print text, '\n'
	if cnt == 5:
		break
	

google_obj['reviews'] = jsonArray
print google_obj
