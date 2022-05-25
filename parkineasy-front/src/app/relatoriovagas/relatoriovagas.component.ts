import { Component, OnInit } from '@angular/core';
import { listaVagas, VagasService } from '../vagas/vagas.services';

@Component({
  selector: 'app-relatoriovagas',
  templateUrl: './relatoriovagas.component.html',
  styleUrls: ['./relatoriovagas.component.css']
})
export class RelatoriovagasComponent implements OnInit {

  public vagasComum: string[] = [];
  public vagasCoOp: string[] = []
  public vagasDef: string[] = []
  public vagasDefOp: string[] = []
  public vagasIdo: string[] = []
  public vagasIdoOp: string[] = []

  constructor(private vagasService: VagasService,) { }

  ngOnInit(): void {
    this.vagasService.listarVagasOrdenadas().subscribe((values) => {
      values.forEach(vaga => {
        if(vaga.descricao === 'COMUM'){
          if(vaga.ocupada){
            console.log('Comum Ocupada')
            this.vagasCoOp = [...this.vagasCoOp,vaga.codigo]
          }else{
            this.vagasComum = [...this.vagasComum,vaga.codigo]
          }
        } else if(vaga.descricao === 'DEFICIENTE'){
          if(vaga.ocupada){
            this.vagasDefOp = [...this.vagasDefOp,vaga.codigo]
          }else{
            this.vagasDef = [...this.vagasDef,vaga.codigo]
          }
        } else {
          if(vaga.ocupada){
            this.vagasIdoOp = [...this.vagasIdoOp,vaga.codigo]
          }else{
            this.vagasIdo = [...this.vagasIdo,vaga.codigo]
          }
        }

      })
    });
  }

}
