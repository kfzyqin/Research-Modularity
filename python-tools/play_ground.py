from StatisticsToolkit import StatisticsToolkit

path_1 = '/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/' \
               'change-to-tournament-selection/'

path_2 = '/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/' \
               'tournament-selection-size-10/'

omega = StatisticsToolkit(path_1, path_2)

print omega.calculate_fitness_significance(sample_size=40)
print omega.calculate_modularity_significance(sample_size=40)

