import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IZipFlixUser } from 'app/shared/model/zip-flix-user.model';

type EntityResponseType = HttpResponse<IZipFlixUser>;
type EntityArrayResponseType = HttpResponse<IZipFlixUser[]>;

@Injectable({ providedIn: 'root' })
export class ZipFlixUserService {
    public resourceUrl = SERVER_API_URL + 'api/zip-flix-users';

    constructor(private http: HttpClient) {}

    create(zipFlixUser: IZipFlixUser): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(zipFlixUser);
        return this.http
            .post<IZipFlixUser>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(zipFlixUser: IZipFlixUser): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(zipFlixUser);
        return this.http
            .put<IZipFlixUser>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http
            .get<IZipFlixUser>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IZipFlixUser[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(zipFlixUser: IZipFlixUser): IZipFlixUser {
        const copy: IZipFlixUser = Object.assign({}, zipFlixUser, {
            dateOfBirth:
                zipFlixUser.dateOfBirth != null && zipFlixUser.dateOfBirth.isValid() ? zipFlixUser.dateOfBirth.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateOfBirth = res.body.dateOfBirth != null ? moment(res.body.dateOfBirth) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((zipFlixUser: IZipFlixUser) => {
                zipFlixUser.dateOfBirth = zipFlixUser.dateOfBirth != null ? moment(zipFlixUser.dateOfBirth) : null;
            });
        }
        return res;
    }
}
