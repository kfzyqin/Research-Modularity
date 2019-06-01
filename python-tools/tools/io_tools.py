import os


def get_immediate_subdirectories(a_dir):
    return [(a_dir + os.sep + name) for name in os.listdir(a_dir)
            if os.path.isdir(os.path.join(a_dir, name))]


def count_number_of_edges(a_grn):
    return sum(x != 0 for x in a_grn)
