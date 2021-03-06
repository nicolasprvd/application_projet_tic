import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IProjet, Projet } from 'app/shared/model/projet.model';
import { ProjetService } from './projet.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IGroupe } from 'app/shared/model/groupe.model';
import { GroupeService } from 'app/entities/groupe/groupe.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IGroupe | IUser;

@Component({
  selector: 'jhi-projet-update',
  templateUrl: './projet-update.component.html'
})
export class ProjetUpdateComponent implements OnInit {
  isSaving = false;
  groupes: IGroupe[] = [];
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    descriptionPDF: [],
    descriptionPDFContentType: [],
    descriptionTexte: [],
    nbEtudiant: [],
    automatique: [],
    archive: [],
    groupeId: [],
    userExtraId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected projetService: ProjetService,
    protected groupeService: GroupeService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projet }) => {
      this.updateForm(projet);

      this.groupeService
        .query({ filter: 'projet-is-null' })
        .pipe(
          map((res: HttpResponse<IGroupe[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IGroupe[]) => {
          if (!projet.groupeId) {
            this.groupes = resBody;
          } else {
            this.groupeService
              .find(projet.groupeId)
              .pipe(
                map((subRes: HttpResponse<IGroupe>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IGroupe[]) => (this.groupes = concatRes));
          }
        });

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(projet: IProjet): void {
    this.editForm.patchValue({
      id: projet.id,
      nom: projet.nom,
      descriptionPDF: projet.descriptionPDF,
      descriptionPDFContentType: projet.descriptionPDFContentType,
      descriptionTexte: projet.descriptionTexte,
      nbEtudiant: projet.nbEtudiant,
      automatique: projet.automatique,
      archive: projet.archive,
      groupeId: projet.groupeId,
      userExtraId: projet.userExtraId
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('projetticApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const projet = this.createFromForm();
    if (projet.id !== undefined) {
      this.subscribeToSaveResponse(this.projetService.update(projet));
    } else {
      this.subscribeToSaveResponse(this.projetService.create(projet));
    }
  }

  private createFromForm(): IProjet {
    return {
      ...new Projet(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      descriptionPDFContentType: this.editForm.get(['descriptionPDFContentType'])!.value,
      descriptionPDF: this.editForm.get(['descriptionPDF'])!.value,
      descriptionTexte: this.editForm.get(['descriptionTexte'])!.value,
      nbEtudiant: this.editForm.get(['nbEtudiant'])!.value,
      automatique: this.editForm.get(['automatique'])!.value,
      archive: this.editForm.get(['archive'])!.value,
      groupeId: this.editForm.get(['groupeId'])!.value,
      userExtraId: this.editForm.get(['userExtraId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjet>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
