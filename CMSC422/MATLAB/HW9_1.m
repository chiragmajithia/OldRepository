syms x
h = (exp(x)-exp(-x))/(exp(x)+exp(-x))
w1 = [1,-2,-3]'
w2 = [0,3,1]'
v = [2,-1]'
in = [-2,1,-1]'
W = [w1 w2]

h1 = w1' * in
h1 = tanh(h1)

h2 = w2' * in
h2 = tanh(h2)

y = v(1)*h1 + v(2)*h2

W = [w1,w2];
a = W'*in
H = tanh(a)

g = [0;0];
Gi = [0 0;0 0;0 0];
err = 1;

dL_dx = -1*H.^2 + 1 %(1 - tanh(x)^2)
dL_dx = [1 - H(1)^2; 1 - H(2)]

dGi1 = err*v(1)*(1 - h1^2)*in
dGi2 = err*v(2)*(1-h2^2)*in
dGi = [dGi1 dGi2]
Gi = Gi - dGi

W = W - 0.1*Gi