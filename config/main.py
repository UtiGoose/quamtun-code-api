import time, os
import io
import sys
from qiskit import QuantumRegister, ClassicalRegister, AncillaRegister, QuantumCircuit, transpile, Aer, execute
from qiskit.circuit.library import MCXGate



sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf8')

res = {'', ''}

nodeSum = int(sys.argv[1])
isH = sys.argv[2]

def gen2(bdh: bool = False):
    img_name = time.strftime("%Y-%m-%d-%H.%M.%S", time.localtime()) + '.png'
    img_path = os.path.join('C:\\static\\image\\', img_name)


    # 顶点的数量
    v_num = nodeSum
    # 边的数量
    e_num = int(nodeSum * (nodeSum - 1) / 2)

    # 边寄存器
    edges = QuantumRegister(e_num, 'e')
    # 顶点寄存器
    vertexes = QuantumRegister(v_num, 'v')
    # 辅助位1寄存器
    aids1 = AncillaRegister(e_num, 'a1')
    # 辅助位2寄存器
    aids2 = AncillaRegister(e_num, 'a2')
    # 输出寄存器
    out = QuantumRegister(1, 'o')
    # 经典位
    c = ClassicalRegister(1, 'c')

    # 生成线路
    circuit = QuantumCircuit(edges, vertexes, aids1, aids2, out, c)

    # 添加门
    # 初始位
    circuit.x(aids2)
    if isH == 'Y':
        circuit.h(vertexes)
    circuit.barrier()  # 画分界线
    # 计算顶点和边是否满足着色条件
    k = 0
    for i in range(v_num):
        for j in range(i + 1, v_num):
            # 判断i,j两顶点是否相同
            circuit.ccx(vertexes[i], vertexes[j], aids1[k])
            circuit.x(vertexes[i])
            circuit.x(vertexes[j])
            circuit.ccx(vertexes[i], vertexes[j], aids1[k])
            circuit.x(vertexes[i])
            circuit.x(vertexes[j])
            # 判断边是否存在
            circuit.ccx(edges[k], aids1[k], aids2[k])

            circuit.barrier()
            k += 1

    if isH == 'Y':
        circuit.h(vertexes)

    # 合并结果
    gate = MCXGate(e_num)
    ct = list(range(e_num * 2 + v_num, e_num * 3 + v_num + 1))
    circuit.append(gate, ct)

    # 测量量子比特
    circuit.measure(out, c)


    circuit.draw('mpl', scale=1, filename=img_path, initial_state=True, plot_barriers=False, justify='left', fold=-1)

    from builtins import str
    ss = str (circuit.clbits)
    str = ss[-3:-2]
    text = ''
    if(str == '0'):
        text = '不能完成染色'
    else:
        text = '可以完成染色'
    print(img_name)
    print(ss)

    return img_name + ',' + text


if __name__ == '__main__':
    # 输入参数-顶点数
    # n = int(input("请输入顶点数："))
    # 是否在前后添加H门
    bdh = True
    # if str(input("是否在顶点线路前后添加H门(输入Y表示添加，默认不添加)：")).upper() == 'Y':
    #     bdh = True

    gen2(bdh)



    from PIL import Image


    # plt.show()
