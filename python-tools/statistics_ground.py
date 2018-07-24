from StatisticsToolkit import StatisticsToolkit

# path_1 = '/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/edge-number-bias-investigation-proportional'
# path_2 = '/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/edge-number-bias-investigation-tournament'

path_1 = '/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/soto'
# path_1 = '/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/tournament-selection-size-3'
path_2 = '/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/soto-asymmetry-p3'

omega = StatisticsToolkit(path_1, path_2)

print "path 1", path_1
print "path 2", path_2

print omega.calculate_edge_number_significance(sample_size=60)
print omega.calculate_edge_number_std_dev_significance(sample_size=60)

print omega.calculate_fitness_significance(sample_size=60)
print omega.calculate_modularity_significance(sample_size=60)

print omega.calculate_most_modularities_significance(sample_size=60)
print omega.calculate_fittest_modularities_significance(sample_size=60)



