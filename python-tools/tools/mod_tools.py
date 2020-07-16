import storage.mod_warehouse as mod_warehouse


def normalize_mod_q(qs, edge_nums):
    normed_mod_qs = []
    for i in range(len(qs)):
        an_edge_num_idx = edge_nums[i] - edge_nums[0]
        max_q = mod_warehouse.max_mod_15_to_50[an_edge_num_idx]
        min_q = mod_warehouse.min_mod_15_to_50[an_edge_num_idx]
        avg_q = mod_warehouse.avg_mod_15_to_50[an_edge_num_idx]
        q = qs[i]

        normed_mod_qs.append((q - avg_q) / (max_q - avg_q))
    return normed_mod_qs