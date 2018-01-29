from numpy import *
from matplotlib.pyplot import *
import util

Si = util.sqrtm(array([[3,2],[2,4]]))
x = dot(random.randn(1000,2),Si)
print x;
plot(x[:,0],x[:,1],'b.')
show(false)
print dot(x.t,x) / real(x.shape[0])

