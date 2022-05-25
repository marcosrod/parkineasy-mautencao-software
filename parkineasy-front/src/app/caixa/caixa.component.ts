import { Component, OnInit } from '@angular/core';
import { caixaObj, VagasService } from '../vagas/vagas.services';

@Component({
  selector: 'app-caixa',
  templateUrl: './caixa.component.html',
  styleUrls: ['./caixa.component.css']
})
export class CaixaComponent implements OnInit {

  public caixaCom = {
    totalCaixa: 0,
    dataReferencia: new Date(),
    tipoVaga : '' };
  public caixaDef = {
    totalCaixa: 0,
    dataReferencia: new Date(),
    tipoVaga : ''};
  public caixaIdo = {
    totalCaixa: 0,
    dataReferencia: new Date(),
    tipoVaga : ''};

    public todayDate = '';

  constructor(private vagasService: VagasService) { }

  ngOnInit(): void {
    this.vagasService.listarCaixa('1').subscribe((values) => {
      this.caixaCom = values;
      let dataHora = new Date(values.dataReferencia);
        let month = (dataHora.getMonth() + 1) < 10 ? '0'+(dataHora.getMonth() + 1) : (dataHora.getMonth() + 1)
          const dateHoje = dataHora.getDate() +
          '/' +
          month +
          '/' +
          dataHora.getFullYear()
      this.todayDate = dateHoje
      console.log(dateHoje)
    });
    this.vagasService.listarCaixa('2').subscribe((values) => {
      this.caixaDef = values;
    });
    this.vagasService.listarCaixa('3').subscribe((values) => {
      this.caixaIdo = values;
    });
  }

}
