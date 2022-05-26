import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';

import { VagaResponse } from './vaga-response';
import { VagasService } from './vagas.services';

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

  constructor(private vagasService: VagasService, private router: Router,private snackBar: MatSnackBar) {}

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

  openSnackBar(message: string) {
    this.snackBar.open(message,'' ,{
      duration: 3000,
    });
  }

  public onAddVaga(addForm: NgForm): void {
    this.vagasService.addVaga(addForm.value).subscribe({
      next: (response: VagaResponse) => {
        let resposta = `
        Vaga criada com sucesso!
        Código da vaga: ${response.codigo}
        Descrição: ${response.descricao}`;
        this.openSnackBar('Cadastro Concluído')
        setTimeout(() => {
          window.location.reload();
        }, 3500)
      },
      error: (error: HttpErrorResponse) => {
        this.openSnackBar(error.error)
      },
    });
  }

  public onEditVaga(addForm: NgForm): void {
    console.log('edit')
    const objVaga = {
      cod: addForm.value.codigoVaga,
      tipo: +addForm.value.tipo
    }
    this.vagasService.editVaga(objVaga).subscribe({
      next: (response: any) => {
        this.openSnackBar('Edição Concluída')
        setTimeout(() => {
          window.location.reload();
        }, 3500)
      },
      error: (error: HttpErrorResponse) => {
        this.openSnackBar('O tipo selecionado para atualização já é o que está cadastrado na vaga.')
      },
    });
  }

  public onDelVaga(cod: string): void {
    this.vagasService.delVaga(cod).subscribe({
      next: (response: any) => {
        this.openSnackBar('Exclusão Concluida')
        setTimeout(() => {
          window.location.reload();
        }, 3500)
      },
      error: (error: HttpErrorResponse) => {
        this.openSnackBar('Erro ao realizar a operação')
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
