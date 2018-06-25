from StatisticsToolkit import StatisticsToolkit

# path_1 = '/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/edge-number-bias-investigation-proportional'
# path_2 = '/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/edge-number-bias-investigation-tournament'

path_1 = '/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/tournament-3-all-combination-perturbations-asym-p4'
# path_1 = '/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/tournament-selection-size-3'
path_2 = '/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/tournament-3-all-combination-perturbations-asym-p2'

omega = StatisticsToolkit(path_1, path_2)

print "path 1", path_1
print "path 2", path_2

print omega.calculate_edge_number_significance(sample_size=40)
print omega.calculate_edge_number_std_dev_significance(sample_size=40)

print omega.calculate_fitness_significance(sample_size=40)
print omega.calculate_modularity_significance(sample_size=40)

