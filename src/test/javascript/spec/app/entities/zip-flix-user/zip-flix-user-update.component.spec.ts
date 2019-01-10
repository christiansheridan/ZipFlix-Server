/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ZipVidzTestModule } from '../../../test.module';
import { ZipFlixUserUpdateComponent } from 'app/entities/zip-flix-user/zip-flix-user-update.component';
import { ZipFlixUserService } from 'app/entities/zip-flix-user/zip-flix-user.service';
import { ZipFlixUser } from 'app/shared/model/zip-flix-user.model';

describe('Component Tests', () => {
    describe('ZipFlixUser Management Update Component', () => {
        let comp: ZipFlixUserUpdateComponent;
        let fixture: ComponentFixture<ZipFlixUserUpdateComponent>;
        let service: ZipFlixUserService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ZipVidzTestModule],
                declarations: [ZipFlixUserUpdateComponent]
            })
                .overrideTemplate(ZipFlixUserUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ZipFlixUserUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ZipFlixUserService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ZipFlixUser('123');
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.zipFlixUser = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ZipFlixUser();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.zipFlixUser = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
