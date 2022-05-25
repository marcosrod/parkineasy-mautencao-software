import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

import { VagaResponse } from './vaga-response';
import { VagasService } from './vagas.services';

// interface VagaTypeObj {
//   [x: string]: number
// }

// const vagaTypes: VagaTypeObj = {
//   COMUM: 1,
//   DEFICIENTE: 2,
//   IDOSO: 3
// }

@Component({
  selector: 'app-root',
  templateUrl: './vagas.component.html',
  styleUrls: ['./vagas.component.css'],
})
export class VagasComponent {
  title = 'parkineasy-front';
  url = './public/images/parkicon.png';
  vagaOpt = 'Criar'
  todasVagas: any[] = []
  listaVagas: string[] = []
  chosenVaga = ''
  chosenTipo = ''
  chosedVaga = {
    codigo: '',
    descricao: ''
  }

  constructor(private vagasService: VagasService, private router: Router) {}

  ngOnInit() {
    this.vagasService.listarVagasOrdenadas().subscribe((values) => {
      let arrVagas: string[] = [];
      this.todasVagas = [...values]
      values.forEach(vaga => {
        arrVagas = [...arrVagas, vaga.codigo]
      })
      this.listaVagas = [...arrVagas]
    });
  }

  public onAddVaga(addForm: NgForm): void {
    this.vagasService.addVaga(addForm.value).subscribe({
      next: (response: VagaResponse) => {
        let resposta = `
        Vaga criada com sucesso!
        Código da vaga: ${response.codigo}
        Descrição: ${response.descricao}`;
        alert(resposta);
        window.location.reload();
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      },
    });
  }

  public onEditVaga(addForm: NgForm): void {
    console.log('edit')
    const objVaga = {
      cod: addForm.value.codigoVaga,
      tipo: addForm.value.tipo
    }
    this.vagasService.editVaga(objVaga).subscribe({
      next: (response: any) => {
        console.log(response)
        window.location.reload();
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      },
    });
  }

  public onDelVaga(cod: string): void {
    this.vagasService.delVaga(cod).subscribe({
      next: (response: any) => {
        console.log(response)
        window.location.reload();
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      },
    });
  }

  public onVaga(addForm: NgForm): void {
    if(this.vagaOpt == 'Criar'){
      this.onAddVaga(addForm)
    } else if(this.vagaOpt == 'Editar'){
      this.onEditVaga(addForm)
    } else if(this.vagaOpt == 'Excluir'){
      this.onDelVaga(addForm.value.codigoVaga)
    }
  }
  
  escolherVaga(value: any): void{
    this.chosenVaga = value;
    const arrChosedVaga = this.todasVagas.filter(vaga => vaga.codigo == value)
    this.chosedVaga = arrChosedVaga[0]
  }

  escolherTipo(value: any): void{
    this.chosenTipo = value;
  }

  mudarOpt(opt: string): void {
    this.vagaOpt = opt;
  }

  voltar(): void {
    this.router.navigateByUrl('/gerencia/funcionarios/cadastro');
  }
}
