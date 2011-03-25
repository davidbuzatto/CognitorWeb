/*
 * Template - é alterado na construção do material.
 */

var dadosMaterial = {
    id: 'arvoreMaterial',
    tipo: 'material',
    titulo: 'Reino Animal',
    strings: {
        btnProximo: 'Próximo',
        btnAnterior: 'Anterior'
    },
    configs: {
        mostrarControles: true,
        gerarRelacionamentos: true
    },
    componentes: [{
        id: 'conceito-1',
        tipo: 'conceito',
        titulo: 'Natureza',
        componentes: [{
            id: 'pagina-1',
            tipo: 'pagina',
            titulo: 'Natureza',
            endereco: 'sco_1.html',
            relacionamentos: [{
                endereco: 'pagina2.html',
                relacao: 'tem como parte'
            }, {
                endereco: 'pagina3.html',
                relacao: 'tem como parte'
            }]
        }, {
            id: 'conceito-2',
            tipo: 'conceito',
            titulo: 'Animal',
            componentes: [{
                id: 'pagina-2',
                tipo: 'pagina',
                titulo: 'Animal',
                endereco: 'pagina2.html',
                relacionamentos: [{
                    endereco: 'pagina1.html',
                    relacao: 'faz parte de'
                }, {
                    endereco: 'pagina3.html',
                    relacao: 'depende de'
                }]
            }]
        }, {
            id: 'conceito-3',
            tipo: 'conceito',
            titulo: 'Vegetal',
            componentes: [{
                id: 'pagina-3',
                tipo: 'pagina',
                titulo: 'Vegetal',
                endereco: 'pagina3.html',
                relacionamentos: [{
                    endereco: 'pagina1.html',
                    relacao: 'também faz parte de'
                }, {
                    endereco: 'pagina2.html',
                    relacao: 'é usado por'
                }]
            }]
        }] // páginas ou conceitos
    }, {
        id: 'grupo-1',
        tipo: 'grupo',
        titulo: 'Grupo 1',
        componentes: [{
            id: 'pagina-4',
            tipo: 'pagina',
            titulo: 'Página 4',
            endereco: 'pagina4.html',
            relacionamentos: []
        }, {
            id: 'grupo-2',
            tipo: 'grupo',
            titulo: 'Grupo 2',
            componentes: [{
                id: 'pagina-5',
                tipo: 'pagina',
                titulo: 'Página 5',
                endereco: 'pagina5.html',
                relacionamentos: []
            }]
        }, {
            id: 'pagina-6',
            tipo: 'pagina',
            titulo: 'Página 6',
            endereco: 'pagina6.html',
            relacionamentos: []
        }] // páginas ou grupos
    }]
};
