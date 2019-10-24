import pandas as pd
import math
import matplotlib.pyplot as plt
import random
import os


path_to_file = "E:\\LP1\\DA\\k-means\\dataset\\iris.csv"

data = pd.read_csv(path_to_file, encoding = 'utf-8')

print(data)