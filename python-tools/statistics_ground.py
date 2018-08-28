from StatisticsToolkit import StatisticsToolkit
import os

prefix_path = os.path.expanduser("~")

path_1 = prefix_path + '/Portal/generated-outputs/combinations-p05'
# path_1 = '/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/tournament-selection-size-3'
path_2 = prefix_path + '/Portal/generated-outputs/combinations-p40'

omega = StatisticsToolkit(path_1, path_2)

print "path 1", path_1
print "path 2", path_2

print('edge number significance')
print omega.calculate_edge_number_significance(sample_size=80)

print('edge number std dev significance')
print omega.calculate_edge_number_std_dev_significance(sample_size=80)

print('fitness significance')
print omega.calculate_fitness_significance(sample_size=80)

print('modularity significance')
print omega.calculate_modularity_significance(sample_size=80)

print('most modularity significance')
print omega.calculate_most_modularities_significance(sample_size=80)

print('fittest modularity significance')
print omega.calculate_fittest_modularities_significance(sample_size=80)



