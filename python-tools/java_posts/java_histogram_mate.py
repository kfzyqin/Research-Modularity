import numpy as np
import matplotlib.pyplot as plt
import sys
import os
import ast

from sys import path
from os.path import dirname as dir

path.append(dir(path[0]))
import utils.io_utils as io_utils

# print sys.argv
# print io_utils.get_current_time()
x_axis = ast.literal_eval(sys.argv[1])
y_tour_axis = ast.literal_eval(sys.argv[2])
y_prop_axis = ast.literal_eval(sys.argv[3])

plt.plot(x_axis, y_tour_axis, color='orange', marker='x')
plt.fill_between(x_axis, y_tour_axis, alpha=0.4, color='orange')

plt.plot(x_axis, y_prop_axis, color='cyan', marker='x')
plt.fill_between(x_axis, y_prop_axis, alpha=0.4, color='cyan')

dir_name = 'generated-images/selection-fitness/'
dir_name = os.path.join(dir_name, io_utils.get_current_time())
if not os.path.exists(dir_name):
    os.makedirs(dir_name)

saving_name = os.path.join(dir_name, 'selection_fitness_gen_{}.png'.format(sys.argv[4]))
plt.savefig(saving_name)

print 'Completed'
# print len(x_axis)


