import StatisticsToolkit
import storage.fitness_warehouse as fit_war

if __name__ == '__main__':
    stat_tool_kit = StatisticsToolkit.StatisticsToolkit()

    candidate_values_gen = [
        fit_war.dist_p00_by_dist_p00_final, fit_war.dist_p00_by_dist_p01_final,
        fit_war.dist_p00_by_stoc_p00_final, fit_war.dist_p00_by_stoc_p01_final,
        fit_war.dist_p01_by_dist_p00_final, fit_war.dist_p01_by_dist_p01_final,
        fit_war.dist_p01_by_stoc_p00_final, fit_war.dist_p01_by_stoc_p01_final,
        fit_war.stoc_p00_by_dist_p00_final, fit_war.stoc_p00_by_dist_p01_final,
        fit_war.stoc_p00_by_stoc_p00_final, fit_war.stoc_p00_by_stoc_p01_final,
        fit_war.stoc_p01_by_dist_p00_final, fit_war.stoc_p01_by_dist_p01_final,
        fit_war.stoc_p01_by_stoc_p00_final, fit_war.stoc_p01_by_stoc_p01_final,
    ]

    candidate_values_strs = [
        'dist_p00_by_dist_p00', 'dist_p00_by_dist_p01',
        'dist_p00_by_stoc_p00', 'dist_p00_by_stoc_p01',
        'dist_p01_by_dist_p00', 'dist_p01_by_dist_p01',
        'dist_p01_by_stoc_p00', 'dist_p01_by_stoc_p01',
        'stoc_p00_by_dist_p00', 'stoc_p00_by_dist_p01',
        'stoc_p00_by_stoc_p00', 'stoc_p00_by_stoc_p01',
        'stoc_p01_by_dist_p00', 'stoc_p01_by_dist_p01',
        'stoc_p01_by_stoc_p00', 'stoc_p01_by_stoc_p01',
    ]

    stat_sz = 100
    for i in range(len(candidate_values_gen)):
        for j in range(i, len(candidate_values_gen)):
            print 'Currently processing: ', candidate_values_strs[i], ' ', candidate_values_strs[j]
            vals_1 = candidate_values_gen[i]
            vals_2 = candidate_values_gen[j]
            print 'size of vals 1', len(vals_1)
            print 'size of vals 2', len(vals_2)
            stat_sig = stat_tool_kit.calculate_statistical_significances(vals_1[:stat_sz], vals_2[:stat_sz])
            print 'stat sig: ', stat_sig
            print '\n'


