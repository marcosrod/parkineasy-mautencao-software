import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-tiposvagas',
  templateUrl: './tiposvagas.component.html',
  styleUrls: ['./tiposvagas.component.css']
})
export class TiposvagasComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit(): void {
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
    this.router.navigateByUrl(`/usuario/vaga/selecionarvaga/${id}`,{state: {vagaType}});
  }
}
