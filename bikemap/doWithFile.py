#!/usr/bin/python

#list directories
import glob, os, sys

#read json files
import json
from pprint import pprint

#mysql
import mysql.connector


dirName = sys.argv[1]
trackId = sys.argv[2]
print("Processing " + dirName + "/" + trackId)

# Read json data
with open(dirName + "/" + trackId) as data_file:    
	gpsData = json.load(data_file)

# Some data has an extra []
if isinstance(gpsData[0][0], list):
	gpsData = gpsData[0]



# db connection
# cnx = mysql.connector.connect(user='root', database='cycling_server')

# # Check if the track was already added
# cursor = cnx.cursor(buffered=True)
# query = ("SELECT id FROM cycling_server.tracks where id = " + trackId + "")
# cursor.execute(query)

# if cursor.rowcount == 1:
# 	sys.exit()

# # mark track as inserted
# cursor = cnx.cursor(buffered=True)
# query = ("INSERT INTO cycling_server.tracks (id) VALUES (" + trackId + ")")
# cursor.execute(query)
# cnx.commit()

maxDistanceBetweenPoints = 0.0001

# split track
trackData = []
for index in range(len(gpsData)):
	gps = gpsData[index]
	if index != 0:
		lastGps = trackData[-1]['data']
		deltaX = float(gps[0]) - float(lastGps[0])
		deltaY = float(gps[1]) - float(lastGps[1])

		nrX = deltaX/maxDistanceBetweenPoints
		nrY = deltaY/maxDistanceBetweenPoints

		nrOp = int(max(nrX, nrY))

		if nrOp == 0:
			continue

		opDeltaX = deltaX/nrOp
		opDeltaY = deltaY/nrOp

		lastX = float(lastGps[0])
		lastY = float(lastGps[1])

		for i in range(nrOp-1):
			lastX += opDeltaX
			lastY += opDeltaY
			trackData.append({'isOriginal': False, 'data': [lastX, lastY]})			


	trackData.append({'isOriginal': True, 'data': [float(gps[0]), float(gps[1])]})

with open(dirName + "/" + trackId + ".points", "w") as text_file:
	text_file.write(str(trackData))


# # get intersections

# for point in trackData:

# 	cursor = cnx.cursor(buffered=True)
# 	query = ("SELECT id FROM cycling_server.tracks_data \
# 				WHERE ST_Distance(point, GeomFromText(\'POINT(" + str(point['data'][0]) + " " + str(point['data'][1]) + ")\')) < " + str(maxDistanceBetweenPoints) + " \
# 				order by ST_Distance(point, GeomFromText(\'POINT(" + str(point['data'][0]) + " " + str(point['data'][1]) + ")\'))")
# 	cursor.execute(query)

# 	# TODO
# 	# print(cursor.rowcount)


# # save intersaction
# # TODO


# # save graph
# # TODO



# # insert points in db
# for point in trackData:
# 	# insert point in db
# 	cursor = cnx.cursor(buffered=True)
# 	query = ("INSERT INTO tracks_data (track_id, point) \
# 				VALUES (" + trackId + ", GeomFromText(\'POINT(" + str(point['data'][0]) + " " + str(point['data'][1]) + ")'))")
# 	cursor.execute(query)
# 	cnx.commit()






# cursor.close()
# cnx.close()
