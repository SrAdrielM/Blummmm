create table pacientee(
UUID_Paciente varchar2(50) primary key,
Nombres varchar2 (50) not null,
Apellidos varchar2(50) not null,
Edad int not null, 
Efermedad varchar(20)not null,
Fecha_Nacimiento varchar(20),
numero_habitacion varchar2(20),
numero_cama number,
UUID_Medicamento varchar2(50),
medicamento_adiccional varchar2 (1000),
hora_aplicacion varchar2(50),
CONSTRAINT fk_medicamentosss FOREIGN KEY (UUID_Medicamento) REFERENCES Medicamentoo(UUID_Medicamento)
);

create table Medicamentoo(
UUID_Medicamento varchar2(50) primary key,
Nombre_medicamento varchar2(50) 
);

create table detalle_PacienteMedicamentoos(
UUID_Paciente varchar2(50),
UUID_Medicamento varchar2(50),
CONSTRAINT fk_medicamentooss FOREIGN KEY (UUID_Medicamento) REFERENCES Medicamentoo(UUID_Medicamento),
CONSTRAINT fk_Pacienteee FOREIGN KEY (UUID_Paciente) REFERENCES pacientee(UUID_Paciente)
);

