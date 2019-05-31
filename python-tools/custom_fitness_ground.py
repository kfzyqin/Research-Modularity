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

    elite_candidate_values_gen = [
        fit_war.elite_dist_p00_by_dist_p00_final,
        # fit_war.elite_dist_p00_by_dist_p01_final,
        # fit_war.elite_dist_p00_by_stoc_p00_final,
        # fit_war.elite_dist_p00_by_stoc_p01_final,
        # fit_war.elite_dist_p01_by_dist_p00_final,
        fit_war.elite_dist_p01_by_dist_p01_final,
        # fit_war.elite_dist_p01_by_stoc_p00_final,
        # fit_war.elite_dist_p01_by_stoc_p01_final,
        # fit_war.elite_stoc_p00_by_dist_p00_final,
        # fit_war.elite_stoc_p00_by_dist_p01_final,
        # fit_war.elite_stoc_p00_by_stoc_p00_final,
        # fit_war.elite_stoc_p00_by_stoc_p01_final,
        # fit_war.elite_stoc_p01_by_dist_p00_final,
        # fit_war.elite_stoc_p01_by_dist_p01_final,
        # fit_war.elite_stoc_p01_by_stoc_p00_final,
        # fit_war.elite_stoc_p01_by_stoc_p01_final,
    ]

    elite_candidate_values_strs = [
        'elite_dist_p00_by_dist_p00',
        # 'elite_dist_p00_by_dist_p01',
        # 'elite_dist_p00_by_stoc_p00',
        # 'elite_dist_p00_by_stoc_p01',
        # 'elite_dist_p01_by_dist_p00',
        'elite_dist_p01_by_dist_p01',
        # 'elite_dist_p01_by_stoc_p00',
        # 'elite_dist_p01_by_stoc_p01',
        # 'elite_stoc_p00_by_dist_p00',
        # 'elite_stoc_p00_by_dist_p01',
        # 'elite_stoc_p00_by_stoc_p00', 'elite_stoc_p00_by_stoc_p01',
        # 'elite_stoc_p01_by_dist_p00', 'elite_stoc_p01_by_dist_p01',
        # 'elite_stoc_p01_by_stoc_p00', 'elite_stoc_p01_by_stoc_p01',
    ]

    stat_sz = 100
    for i in range(len(elite_candidate_values_gen)-1):
        for j in range(i+1, len(elite_candidate_values_gen)):
            print 'Currently processing: ', elite_candidate_values_strs[i], ' ', elite_candidate_values_strs[j]
            vals_1 = elite_candidate_values_gen[i]
            vals_2 = elite_candidate_values_gen[j]
            print 'size of vals 1', len(vals_1)
            print 'size of vals 2', len(vals_2)
            stat_sig = stat_tool_kit.calculate_statistical_significances(vals_1[:stat_sz], vals_2[:stat_sz])
            print 'stat sig: ', stat_sig
            print '\n'


