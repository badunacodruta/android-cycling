#!/usr/bin/python

# list directories
import glob, os, sys

# read json files
import json
from pprint import pprint

# mysql
import mysql.connector

# sqrt
from math import sqrt


dirName = sys.argv[1]
trackId = sys.argv[2]
print("Processing " + dirName + "/" + trackId + ".points")

# Read json data
with open(dirName + "/" + trackId + ".points") as data_file:    
	text = data_file.read().replace('\'', '"')
	text = text.replace('True', 'true')
	text = text.replace('False', 'false')
	trackData = json.loads(text)




# db connection
cnx = mysql.connector.connect(user='root', database='cycling_server')

# # # Check if the track was already added
# cursor = cnx.cursor(buffered=True)
# query = ("SELECT id FROM cycling_server.tracks where id = " + trackId + "")
# cursor.execute(query)

# # if cursor.rowcount == 1:
# # 	sys.exit()

# # # mark track as inserted
# cursor = cnx.cursor(buffered=True)
# query = ("INSERT INTO cycling_server.tracks (id) VALUES (" + trackId + ")")
# cursor.execute(query)
# cnx.commit()


maxDistanceBetweenPoints = 0.0001
segment_size = '.5'


# get intersections
last_seg_x = -1
last_seg_y = -1
last_cursor = []
for point in trackData:
	x = float(point['data'][0])
	y = float(point['data'][1])
	seg_x = float(format(x, segment_size))
	seg_y = float(format(y, segment_size))

	if seg_x == last_seg_x and seg_y == last_seg_y:
		cursor = last_cursor
	else :
		cursor = cnx.cursor(buffered=True)
		query = ("SELECT id, x, y FROM cycling_server.tracks_data \
					WHERE seg_x in (%s, %s, %s) AND seg_y in (%s, %s, %s)" % (seg_x-1, seg_x, seg_x+1, seg_y-1, seg_y, seg_y+1,))
		cursor.execute(query)

	last_seg_x = seg_x
	last_seg_y = seg_y
	last_cursor = []
	for (point_id, point_x, point_y) in cursor:
		last_cursor.append([point_id, point_x, point_y])
		dX = x - point_x
		dY = y - point_y
		if sqrt(dX*dX + dY*dY) <= maxDistanceBetweenPoints:
			# points close enought
			if not 'neighbour_distance' in point or sqrt(dX*dX + dY*dY) < point['neighbour_distance']:
				point['neighbour_id'] = point_id
				point['neighbour_distance'] = sqrt(dX*dX + dY*dY)


# insert points in db
for point in trackData:
	x = float(point['data'][0])
	y = float(point['data'][1])
	seg_x = format(x, segment_size)
	seg_y = format(y, segment_size)

	cursor = cnx.cursor(buffered=True)
	query = ("INSERT INTO tracks_data (track_id, seg_x, seg_y, x, y) VALUES (%s, %s, %s, %s ,%s)" % (trackId, seg_x, seg_y, x, y))
	cursor.execute(query)
	point['id'] = cursor.lastrowid
	cnx.commit()

# create line
allPoints = []
for point in trackData:	
	allPoints.append(point['id'])
	if 'neighbour_id' in point:
		allPoints.append(point['neighbour_id'])

# create org.collaborative.cycling.services.track.graph
org.collaborative.cycling.services.track.graph = []
for index in range(len(allPoints)-1):
	point_id = allPoints[index]
	next_point_id = allPoints[index+1]
	org.collaborative.cycling.services.track.graph.append([point_id, next_point_id])

# save org.collaborative.cycling.services.track.graph to file
with open(dirName + "/" + trackId + ".org.collaborative.cycling.services.track.graph", "w") as text_file:
	text_file.write(str(org.collaborative.cycling.services.track.graph))


cursor.close()
cnx.close()
