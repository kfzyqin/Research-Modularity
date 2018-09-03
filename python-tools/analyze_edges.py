from EdgeNumberTool import EdgeNumberTool
import os
from StatisticsToolkit import StatisticsToolkit

a_path_1 = os.path.expanduser("~") + '/Portal/generated-outputs/balanced-combinations-p00'
a_path_2 = os.path.expanduser("~") + '/Portal/generated-outputs/balanced-combinations-p01'

edge_number_tool = EdgeNumberTool()
# edge_nos_1 = edge_number_tool.get_average_inter_module_edges_for_an_experiment(100, 'fit', a_path_1)
# edge_nos_2 = edge_number_tool.get_average_inter_module_edges_for_an_experiment(100, 'fit', a_path_2)
#
# print("length 1: ", len(edge_nos_1))
# print("length 2: ", len(edge_nos_2))
#
# print StatisticsToolkit.calculate_statistical_significances(edge_nos_1, edge_nos_2)

targets_1 = ['2018-08-27-10-54-33', '2018-08-27-10-55-14', '2018-08-27-11-58-52']
targets_2 = ['2018-08-28-07-05-11', '2018-08-28-07-05-13', '2018-08-28-07-37-11',
             '2018-08-28-11-39-36', '2018-08-28-11-39-52', '2018-08-28-13-41-10', '2018-08-28-16-49-15']

for a_path in targets_1:
    the_path = a_path_1 + os.sep + a_path
    print edge_number_tool.get_average_edge_number_of_the_whole_trial(the_path)