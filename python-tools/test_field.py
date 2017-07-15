import numpy as np

grn = np.matrix([
    [1, 0, 0],
    [0, 1, -1],
    [-1, -1, -1]
])

start = np.matrix([-1, -1, -1])

# print np.dot(start, grn)

tmp = start
for i in range(20):
    tmp = np.dot(tmp, grn)

print tmp