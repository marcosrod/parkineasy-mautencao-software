import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { VagasService } from '../vagas/vagas.services';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-tiposvagas',
  templateUrl: './tiposvagas.component.html',
  styleUrls: ['./tiposvagas.component.css']
})
export class TiposvagasComponent implements OnInit {

  constructor(private router:Router,private vagasService: VagasService,private snackBar: MatSnackBar) { }

  ngOnInit(): void {
  }

  openSnackBar(message: string) {
    this.snackBar.open(message,'' ,{
      duration: 3000,
    });
  }

  public checarVaga(vagaId: number, vagaType: string){
    this.vagasService.listarVagasPorTipo(vagaId).subscribe((params) => {
        if(params.length > 0){
          this.router.navigateByUrl(`/usuario/vaga/selecionarvaga/${vagaId}`,{state: {vagaType}});
        }else {
          this.openSnackBar('O Tipo de Vaga Selecionado Não Possui Vagas Livres')
        }
    });
  }


  public listarVagas(id:number){
    let vagaType = '';
    if(id === 1){
      vagaType = 'Comum'
    } else if(id === 2){
      vagaType = 'Deficiente'
    } else {
      vagaType = 'Idoso'
    }
    this.checarVaga(id,vagaType)
  }
}
