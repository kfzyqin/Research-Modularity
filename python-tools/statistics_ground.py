from StatisticsToolkit import StatisticsToolkit

# path_1 = '/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/' \
#                'tournament-selection-size-3/'

path_1 = '/Volumes/Chin-Soundwave/Chin-GA-Project/generated-outputs/' \
               'larson-with-perturbation-recording'

# path_2 = '/Volumes/Chin-Soundwave/Chin-GA-Project/generated-outputs/' \
#                'tournament-selection-size-2'

path_2 = '/Volumes/Chin-Soundwave/Chin-GA-Project/thesis-data/' \
               'different-crossover-mechanism-comparisons/chin-crossover'

omega = StatisticsToolkit(path_1, path_2)

print "path 1", path_1
print "path 2", path_2

print omega.calculate_fitness_significance(sample_size=40)
print omega.calculate_modularity_significance(sample_size=40)

