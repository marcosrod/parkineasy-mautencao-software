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
  public vagasGerais: any[] = []

  constructor(private vagasService: VagasService,) { }

  ngOnInit(): void {
    this.vagasService.listarVagasOrdenadasPrefixo().subscribe((values) => {
      this.vagasGerais = [...values]
      console.log(values)
    });
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

  setVagaCor(vaga: any): string {
    if(vaga.descricao == 'COMUM'){
      return this.vagasComum.includes(vaga.codigo) ? '#009688' : '#80CBC4'
    }

    if(vaga.descricao == 'DEFICIENTE'){
      return this.vagasDef.includes(vaga.codigo) ? '#3F51B5' : '#9FA8DA'
    }

    if(vaga.descricao == 'IDOSO'){
      return this.vagasIdo.includes(vaga.codigo) ? '#673AB7' : '#B39DDB'
    }
    
    return ''
  }

}
