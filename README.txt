11/03/2023


Autalizações:
1 -Animaçã de morte da galinha, é configuração da mesma.
2-Começo da criação de uma nova função no movimentos da entitys.


Descrições:
1- Criaaçãodos sprites de morte, configurações de animação é logica por trás da animeação, as autulizações até o momento são básica.
2-Subida em escada ou barrancos, configuração finalizada, com as mudificações feitas na classe player e world. Na world temos a criação
de duas novas funções de verificações de steps e na player temos mudançãs na função "logicOfMove" com a criação de verificações para
movimentos em subidas e decidas de escada, para os dois lados.

Tarefas futuras:
1- Arrumar o bug das steps que não estão funcinando direito ao aperta para baixo.
2- Arrumar a renderização para as subidas na steps.
3- Fazer com que a inteligencia artificial tenhas os movimentos de subidas é decidas das steps, junto com as renderezações.


12/03/2023
Tarefas Futuras feitas(dai anterior):
1,3

Autalizações:

1-Sistema de subidas em steps.

2-Sistema de subidas em steps na inteligincia artificial.

Descrições:
1- Está funcinando perfeitamente. Foi concertado o bag da subida e descida segurando o botão para baixo, junto
com dois bugs de renderização, foram feitas modificações apenas na função "logicOfMove", sendo acresentado mais 4 a 8 else e if. Recomendo
revisão é manutemção no código para aprimorar é organizar a ordem dos fatores é criações de função para acontecimentos repetidos.

2 - Funcinado perfeitamente. Foram adicionados mais de 4 if e else para a configuração de analise do map na inteligencia, como mudanças 
siginificativas nas formas de analise de platarformas e movimentos, como a troca de direção. Recomendo
revisão é manutemção no código para aprimorar é organizar a ordem dos fatores é criações de função para acontecimentos repetidos.

Bugs:
Zero bugs encontrado no momento

Tarefas futuras:

1- Organizar os metodos "render" e "logicOfMove" da classe player.
2- Renderização do sistema de subinda em escadas.
3- Configurar o sistemas de pulo do player, pois não esta identificando as steps 