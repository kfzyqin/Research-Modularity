from StatisticsToolkit import StatisticsToolkit
import tools.storage.stat_warehouse as stat_warehouse
import tools.storage.fitness_warehouse as fitness_warehouse

statistics_toolkit = StatisticsToolkit()
print 'len of values 1: ', len(fitness_warehouse.stoc_p00_by_dist_p00_final)
print 'len of values 2: ', len(fitness_warehouse.elite_stoc_p00_by_dist_p00_final)
print statistics_toolkit.calculate_statistical_significances(fitness_warehouse.stoc_p00_by_dist_p00_final,
                                                             fitness_warehouse.elite_stoc_p00_by_dist_p00_final)
