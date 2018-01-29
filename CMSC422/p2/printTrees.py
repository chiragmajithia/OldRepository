from sklearn.tree import DecisionTreeClassifier
import multiclass
import util
from datasets import *

def printAllOVATrees(data,max_depth = 1):
	h = multiclass.OAA(len(data.labels), lambda: DecisionTreeClassifier(max_depth=max_depth))
	h.train(data.X, data.Y)
	cnt = 0;
	for tree in h.f:
		print("Wine:: " + data.labels[cnt])
		util.showTree(tree,data.words)
		raw_input();
		cnt  = cnt + 1

def printAllAVATrees(data,max_depth = 1):
	h = multiclass.AVA(len(data.labels), lambda: DecisionTreeClassifier(max_depth=max_depth))
	h.train(data.X,data.Y)
	cnt = 0;
	for i in range(1,len(data.labels)):
		for j in range(1,len(data.labels)):
			try:
				if h.f[i][j] is not None:
					print('['+str(i)+','+str(j)+']')
					util.showTree(h.f[i][j],data.words)
					raw_input();
					j = j + 1
			except IndexError:
				print "Exception"
				pass
		i = i + 1