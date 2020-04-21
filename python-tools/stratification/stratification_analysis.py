from data_storage.data_storage_2020 import dist_x_p00_by_dist_p00_final
import file_processor as fp

a_fit_list = dist_x_p00_by_dist_p00_final

print len(a_fit_list)

print sorted(set(a_fit_list))
fp.save_lists_graph([sorted(a_fit_list)])
