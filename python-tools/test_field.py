import numpy as np

# grn = np.array([
#     [1, 0.5, 1],
#     [1, 0, 0],
#     [0, 0.5, 0]
# ])

grn = np.array([
    [0, 0.5, 0.5],
    [0, 0, 0.5],
    [1, 0.5, 0]
])

start = np.array([1, 1, 1])

# print np.dot(start, grn)

tmp = start

for i in range(1):
    tmp = np.dot(grn, tmp)

# tmp = tmp / sum(tmp)
print tmp

