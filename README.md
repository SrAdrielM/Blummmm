create table paciente(
UUID_Paciente varchar2(50) primary key,
Nombres varchar2 (50) not null,
Apellidos varchar2(50) not null,
Edad int not null, 
Efermedad varchar(20)not null,
Fecha_Nacimiento varchar(20),
numero_habitacion varchar2(20),
numero_cama number,
medicamento_adiccional varchar2 (1000),
UUID_Medicamento varchar2(50),
CONSTRAINT fk_medicamentoo FOREIGN KEY (UUID_Medicamento) REFERENCES Medicamento(UUID_Medicamento)
);

create table Medicamento(
UUID_Medicamento varchar2(50) primary key,
Nombre_medicamento varchar2(50) 
);

create table detalle_PacienteMedicamentos(
UUID_Paciente varchar2(50),
UUID_Medicamento varchar2(50),
hora_aplicacion varchar2(50),
CONSTRAINT fk_medicamentoos FOREIGN KEY (UUID_Medicamento) REFERENCES Medicamento(UUID_Medicamento),
CONSTRAINT fk_Paciente FOREIGN KEY (UUID_Paciente) REFERENCES paciente(UUID_Paciente)
);
