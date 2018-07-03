import re


def get_matches_from_a_string(re_exp, a_str):
    rtns = []
    phenotype_patter = re.compile(re_exp)
    for match in re.finditer(phenotype_patter, a_str):
        tmp = match.group()
        rtns.append(tmp)
    return rtns


def get_grns_from_a_grn_file(file_name):
    lines = []

    with open(file_name) as f:
        content = f.readlines()
        for a_line in content:
            tmp_line = a_line.replace('[', '').replace(']', '').replace('\n', '')
            lines.append(map(int, tmp_line.split(',')))

    return lines

