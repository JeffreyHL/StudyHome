#注解（Annotation）

　　Annotation 是一种元程序中的元素关联任何信息和任何*Metadata（元数据）*的途径和方法。Annotation是一个接口，程序可以通过反射来获取指定程序元素的Annotation对象，然后通过Annotation对象来获取注解里面的元数据。Annotation的行为十分类似public、final这样的修饰符。每个Annotation具有一个名字和成员个数>=0。每个Annotation的成员具有被称为name=value对的名字和值（就像javabean一样），name=value装载了Annotation的信息。

　　Annotation是JDK5.0及以后版本引入的。它可以用于创建文档，跟踪代码中的依赖性，甚至执行基本编译时检查。从某些方面看，Annotation就像修饰符一样被使用，并应用于包、类型、构造方法、方法、成员变量、参数、本地变量的声明中。这些信息被存储在Annotation的“name=value”结构对中。

　　Annotation的成员在Annotation类型中以无参数的方法的形式被声明。其方法名和返回值定义了该成员的名字和类型。在此有一个特定的默认语法：允许声明任何Annotation成员的默认值：一个Annotation可以将name=value对作为没有定义默认值的Annotation成员的值，当然也可以使用name=value对来覆盖其它成员默认值。这一点有些近似类的继承特性，父类的构造函数可以作为子类的默认构造函数，但是也可以被子类覆盖。

　　Annotation能被用来为某个程序元素（类、方法、成员变量等）关联任何的信息。需要注意的是，这里存在着一个基本的规则：Annotation不能影响程序代码的执行，无论增加、删除 Annotation，代码都始终如一的执行。另外，尽管一些Annotation通过java的反射api方法在运行时被访问，而java语言解释器在工作时忽略了这些Annotation。正是由于java虚拟机忽略了Annotation，导致了Annotation类型在代码中是“不起作用”的； 只有通过某种配套的工具才会对Annotation类型中的信息进行访问和处理。

#元数据（Metadata）

　　Metadata 就是“关于数据的数据”。Metadata的功能作用有很多，比如：你可能用过Javadoc的注释自动生成文档。总的来说，Metadata可以用来创建文档，跟踪代码的依赖性，执行编译时格式检查，代替已有的配置文件。如果要对于Metadata的作用进行分类，目前还没有明确的定义，不过我们可以根据它所起的作用，大致可分为三类： 
1.	编写文档：通过代码里标识的元数据生成文档
2.	代码分析：通过代码里标识的元数据对代码进行分析
3.	编译检查：通过代码里标识的元数据让编译器能实现基本的编译检查

　　在Java中Metadata以标签的形式存在于Java代码中，Metadata标签的存在并不影响程序代码的编译和执行，它只是被用来生成其它的文件或针在运行时知道被运行代码的描述信息。

　　综上所述：
1.	Metadata以标签的形式存在于Java代码中。
2.	Metadata描述的信息是类型安全的，即Metadata内部的字段都是有明确类型的。
3.	Metadata需要编译器之外的工具额外的处理用来生成其它的程序部件。
4.	Metadata可以只存在于Java源代码级别，也可以存在于编译之后的Class文件内部。

#Annotation类型：

　　Annotation类型定义了Annotation的名字、类型、成员默认值。一个Annotation类型可以说是一个特殊的java接口，它的成员变量是受限制的，而声明Annotation类型时需要使用新语法。当我们通过java反射api访问Annotation时，返回值将是一个实现了该Annotation类型接口的对象，通过访问这个对象我们能方便的访问到其Annotation成员。

　　注意：我们可以在下面的情况中缩写Annotation：当Annotation只有单一成员，并成员命名为"value="。这时可以省去"value="。

　　根据注解参数的个数，我们可以将注解分为三类：
1.	标记注解:一个没有成员定义的Annotation类型被称为标记注解。这种Annotation类型仅使用自身的存在与否来为我们提供信息。比如后面的系统注解@Override;
2.	单值注解
3.	完整注解　　

　　根据注解使用方法和用途，我们可以将Annotation分为三类：
1.	JDK内置系统注解<br>
	注解的语法比较简单，除了@符号的使用外，他基本与Java固有的语法一致，JavaSE中内置三个标准注解，定义在java.lang中：
　<br>@Override：修饰此方法覆盖了父类的方法；
　<br>@Deprecated：修饰已经过时的方法；
　<br>@SuppressWarnnings：抑制编译器警告，它有一个类型为String[]的成员，这个成员的值为被禁止的警告名。常见参数值：
　　　　<br>　　1.deprecation：使用了不赞成使用的类或方法时的警告；
　　　　<br>　　2.unchecked：执行了未检查的转换时的警告，例如当使用集合时没有用泛型 (Generics) 来指定集合保存的类型; 
　　　　<br>　　3.fallthrough：当 Switch 程序块直接通往下一种情况而没有 Break 时的警告;
　　　　<br>　　4.path：在类路径、源文件路径等中有不存在的路径时的警告; 
　　　　<br>　　5.serial：当在可序列化的类上缺少 serialVersionUID 定义时的警告; 
　　　　<br>　　6.finally：任何 finally 子句不能正常完成时的警告; 
　　　　<br>　　7.all：关于以上所有情况的警告。
2.	元注解<br>
	元注解的作用就是负责注解其他注解。Java5.0定义的元注解：
　<br>@Target：描述注解的使用范围。可被用于 packages、types（类、接口、枚举、Annotation类型）、类型成员（方法、构造方法、成员变量、枚举值）、方法参数和本地变量（如循环变量、catch参数）。取值(ElementType)有：
　　　　<br>　　CONSTRUCTOR：用于描述构造器
　　　　<br>　　FIELD：用于描述域
　　　　<br>　　LOCAL_VARIABLE：用于描述局部变量
　　　　<br>　　METHOD：用于描述方法
　　　　<br>　　PACKAGE：用于描述包
　　　　<br>　　PARAMETER：用于描述参数
　　　　<br>　　TYPE：用于描述类、接口(包括注解类型) 或enum声明
　<br>@Retention：描述注解的生命周期，表示需要在什么级别保存该注释信息。取值（RetentionPoicy）有：
　　　　<br>　　SOURCE:在源文件中有效（即源文件保留）
　　　　<br>　　CLASS:在class文件中有效（即class保留）
　　　　<br>　　RUNTIME:在运行时有效（即运行时保留）
　<br>@Documented：描述其它类型的Annotation应该被作为被标注的程序成员的公共API，因此可以被例如javadoc此类的工具文档化。标记注解，没有成员。
　<br>@Inherited：描述了某个被标注的类型是被继承的。标记注解，没有成员。
　　　　<br>　　注意：@Inherited 注解是被标注过的class的子类所继承。类并不从它所实现的接口继承注解，方法并不从它所重载的方法继承注解。
3.	自定义注解<br>
　　使用@interface自定义注解时，自动继承了java.lang.annotation.Annotation接口，由编译程序自动完成其他细节。在定义注解时，不能继承其他的注解或接口。@interface用来声明一个注解，其中的每一个方法实际上是声明了一个配置参数。方法的名称就是参数的名称，返回值类型就是参数的类型（返回值类型只能是基本类型、Class、String、enum）。可以通过default来声明参数的默认值。
　　<br><br>定义注解格式： public @interface 注解名 {定义体}
　　<br><br>注解参数的可支持数据类型：
　　　　<br>　　1.所有基本数据类型（int,float,boolean,byte,double,char,long,short)
　　　　<br>　　2.String类型
　　　　<br>　　3.Class类型
　　　　<br>　　4.enum类型
　　　　<br>　　5.Annotation类型
　　　　<br>　　6.以上所有类型的数组
　　<br><br>注解类型里面的参数该怎么设定: 
　　　　<br>　　第一,只能用public或默认(default)这两个访问权修饰.例如,String value();这里把方法设为defaul默认类型；　 　
　　　　<br>　　第二,参数成员只能用基本类型byte,short,char,int,long,float,double,boolean八种基本数据类型和 String,Enum,Class,annotations等数据类型,以及这一些类型的数组.例如,String value();这里的参数成员就为String;　　
　　　　<br>　　第三,如果只有一个参数成员,最好把参数名称设为"value",后加小括号。
　　<br><br>注解元素的默认值：
　　　　<br>　　注解元素必须有确定的值，要么在定义注解的默认值中指定，要么在使用注解时指定，非基本类型的注解元素的值不可为null。因此, 使用空字符串或0作为默认值是一种常用的做法。这个约束使得处理器很难表现一个元素的存在或缺失的状态，因为每个注解的声明中，所有元素都存在，并且都具有相应的值，为了绕开这个约束，我们只能定义一些特殊的值，例如空字符串或者负数，一次表示某个元素不存在，在定义注解时，这已经成为一个习惯用法。

#注解处理器
　　注解处理器类库(java.lang.reflect.AnnotatedElement)：

　　Java使用Annotation接口来代表程序元素前面的注解，该接口是所有Annotation类型的父接口。除此之外，Java在java.lang.reflect 包下新增了AnnotatedElement接口，该接口代表程序中可以接受注解的程序元素，该接口主要有如下几个实现类：

　　Class：类定义
　　Constructor：构造器定义
　　Field：累的成员变量定义
　　Method：类的方法定义
　　Package：类的包定义

　　java.lang.reflect 包下主要包含一些实现反射功能的工具类，实际上，java.lang.reflect 包所有提供的反射API扩充了读取运行时Annotation信息的能力。当一个Annotation类型被定义为运行时的Annotation后，该注解才能是运行时可见，当class文件被装载时被保存在class文件中的Annotation才会被虚拟机读取。
　　AnnotatedElement 接口是所有程序元素（Class、Method和Constructor）的父接口，所以程序通过反射获取了某个类的AnnotatedElement对象之后，程序就可以调用该对象的如下四个个方法来访问Annotation信息：

　　方法1：<T extends Annotation> T getAnnotation(Class<T> annotationClass): 返回改程序元素上存在的、指定类型的注解，如果该类型注解不存在，则返回null。

　　方法2：Annotation[] getAnnotations():返回该程序元素上存在的所有注解。

　　方法3：boolean is AnnotationPresent(Class<?extends Annotation> annotationClass):判断该程序元素上是否包含指定类型的注解，存在则返回true，否则返回false.

　　方法4：Annotation[] getDeclaredAnnotations()：返回直接存在于此元素上的所有注释。与此接口中的其他方法不同，该方法将忽略继承的注释。（如果没有注释直接存在于此元素上，则返回长度为零的一个数组。）该方法的调用者可以随意修改返回的数组；这不会对其他调用者返回的数组产生任何影响。