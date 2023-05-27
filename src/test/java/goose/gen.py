from qiskit import QuantumRegister, ClassicalRegister, AncillaRegister, QuantumCircuit, transpile, Aer, execute
from qiskit.circuit.library import MCXGate


def gen2(n: int, img_path: str, bdh: bool = False,):

    # 顶点的数量
    v_num = n
    # 边的数量
    e_num = int(n * (n - 1) / 2)

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
    if bdh:
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

    if bdh:
        circuit.h(vertexes)

    # 合并结果
    gate = MCXGate(e_num)
    ct = list(range(e_num * 2 + v_num, e_num * 3 + v_num + 1))
    circuit.append(gate, ct)

    # 测量量子比特
    circuit.measure(out, c)

    print(circuit)

    circuit.draw('mpl', scale=1, filename=img_path, initial_state=True, plot_barriers=False, justify='left', fold=-1)

    return img_path